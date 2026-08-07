package cc.ffreitasb.nomadhandheld.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.data.model.Categories
import cc.ffreitasb.nomadhandheld.data.repository.AppCatalogRepository
import cc.ffreitasb.nomadhandheld.data.repository.ProgressCalculator
import cc.ffreitasb.nomadhandheld.data.repository.ProgressRepository
import cc.ffreitasb.nomadhandheld.data.source.PackageDetector
import cc.ffreitasb.nomadhandheld.ui.model.AppWithStatus
import cc.ffreitasb.nomadhandheld.ui.model.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home/Dashboard screen.
 * Also drives the Field Sheet screen (which shares the same data).
 *
 * Responsibilities:
 * 1. Load the curated app catalog (once, on init).
 * 2. Sync PackageManager state → DataStore on startup.
 * 3. Combine catalog + live DataStore statuses into [HomeUiState].
 * 4. Expose [markAsReady] / [markAsInstalled] for user interactions.
 *
 * Uses [AndroidViewModel] to access Application context without leaking Activity context.
 * No Hilt in v1 — repositories are instantiated directly.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val catalogRepo = AppCatalogRepository(application)
    private val progressRepo = ProgressRepository(application)

    /** Cached catalog — loaded once on init, never changes at runtime. */
    private val _catalogLoaded = MutableStateFlow<Result<List<cc.ffreitasb.nomadhandheld.data.model.AppEntry>>?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        _catalogLoaded,
        progressRepo.getAllStatuses()
    ) { catalogResult, statuses ->
        when {
            catalogResult == null -> HomeUiState.Loading
            catalogResult.isFailure -> HomeUiState.Error(
                catalogResult.exceptionOrNull()?.message ?: "Erro ao carregar catálogo"
            )
            else -> buildSuccessState(catalogResult.getOrThrow(), statuses)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    init {
        loadCatalogAndSync()
    }

    // ─── Initialization ───────────────────────────────────────────────────────

    private fun loadCatalogAndSync() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching { catalogRepo.getApps() }
            _catalogLoaded.value = result

            // After catalog is loaded, sync PackageManager state
            result.getOrNull()?.let { apps ->
                syncPackageManagerState(apps)
            }
        }
    }

    /**
     * Checks each app via PackageManager and updates DataStore accordingly.
     * Called on app start and whenever this ViewModel is re-created.
     *
     * See [ProgressRepository.syncFromPackageManager] for the transition rules.
     */
    private suspend fun syncPackageManagerState(
        apps: List<cc.ffreitasb.nomadhandheld.data.model.AppEntry>
    ) {
        val context = getApplication<Application>()
        // Batch check all package names in one pass
        val installationMap = PackageDetector.checkAll(
            context = context,
            packageNames = apps.map { it.packageName }
        )
        // Map packageName → appId for lookup
        val packageToId = apps.associate { it.packageName to it.id }

        installationMap.forEach { (packageName, isInstalled) ->
            val appId = packageToId[packageName] ?: return@forEach
            progressRepo.syncFromPackageManager(appId, isInstalled)
        }
    }

    // ─── User actions ─────────────────────────────────────────────────────────

    /**
     * User manually marks an app as READY (fully configured).
     * This is the only way to reach READY status — PackageManager never sets it.
     */
    fun markAsReady(appId: String) {
        viewModelScope.launch { progressRepo.setStatus(appId, AppStatus.READY) }
    }

    /**
     * Reverts an app from READY back to INSTALLED.
     * Exposed for the "undo" action in the card detail screen.
     */
    fun unmarkReady(appId: String) {
        viewModelScope.launch { progressRepo.setStatus(appId, AppStatus.INSTALLED) }
    }

    /**
     * Resets all progress — called from Settings screen.
     */
    fun resetAllProgress() {
        viewModelScope.launch { progressRepo.resetAllProgress() }
    }

    /**
     * Re-triggers PackageManager sync (called when returning from another app,
     * e.g., after the user installed something from the Play Store).
     */
    fun refreshInstallationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = (_catalogLoaded.value?.getOrNull()) ?: return@launch
            syncPackageManagerState(apps)
        }
    }

    // ─── UI state builder ─────────────────────────────────────────────────────

    private fun buildSuccessState(
        apps: List<cc.ffreitasb.nomadhandheld.data.model.AppEntry>,
        statuses: Map<String, AppStatus>
    ): HomeUiState.Success {
        val appsWithStatus = apps.map { app ->
            AppWithStatus(
                app = app,
                status = statuses[app.id] ?: AppStatus.NOT_INSTALLED
            )
        }

        val byCategory = Categories.all.map { category ->
            category to appsWithStatus.filter { it.app.category == category.id }
        }

        val progress = ProgressCalculator.calculate(apps, statuses)

        val criticalApps = appsWithStatus.filter {
            it.app.priority == cc.ffreitasb.nomadhandheld.data.model.AppPriority.CRITICAL
        }

        return HomeUiState.Success(
            categories = byCategory,
            progress = progress,
            criticalApps = criticalApps
        )
    }
}
