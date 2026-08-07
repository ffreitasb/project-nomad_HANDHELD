package cc.ffreitasb.nomadhandheld.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.data.repository.AppCatalogRepository
import cc.ffreitasb.nomadhandheld.data.repository.ProgressRepository
import cc.ffreitasb.nomadhandheld.ui.model.AppWithStatus
import cc.ffreitasb.nomadhandheld.ui.model.CardDetailUiState
import cc.ffreitasb.nomadhandheld.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the Card Detail screen.
 *
 * Loads the specific [AppEntry] by ID (from SavedStateHandle navigation args)
 * and combines it with the live DataStore status flow.
 * Also loads the bundled onboarding markdown content.
 */
class CardDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val appId: String = checkNotNull(savedStateHandle[Screen.CardDetail.ARG_APP_ID])

    private val catalogRepo = AppCatalogRepository(application)
    private val progressRepo = ProgressRepository(application)

    /** One-shot flow that loads the app entry + onboarding markdown from assets. */
    private val appDataFlow = flow {
        val app = catalogRepo.getAppById(appId)
        val markdown = app?.let { catalogRepo.getOnboardingContent(it) }
        emit(Pair(app, markdown))
    }

    val uiState: StateFlow<CardDetailUiState?> = combine(
        appDataFlow,
        progressRepo.getStatus(appId)
    ) { (app, markdown), status ->
        app?.let {
            CardDetailUiState(
                app = AppWithStatus(it, status),
                onboardingMarkdown = markdown
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun markAsReady() {
        viewModelScope.launch(Dispatchers.IO) {
            progressRepo.setStatus(appId, AppStatus.READY)
        }
    }

    fun unmarkReady() {
        viewModelScope.launch(Dispatchers.IO) {
            progressRepo.setStatus(appId, AppStatus.INSTALLED)
        }
    }
}
