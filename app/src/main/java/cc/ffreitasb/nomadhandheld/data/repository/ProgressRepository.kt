package cc.ffreitasb.nomadhandheld.data.repository

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.progressDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Persists and retrieves the installation/configuration status of each curated app.
 *
 * Storage: DataStore<Preferences>, one key per app ID.
 * Key format: "status_{appId}" → AppStatus.name (String)
 *
 * Design decisions:
 * - Keys are strings (not typed enums) for forward compatibility.
 * - AppStatus.fromString() handles unknown/null values gracefully (→ NOT_INSTALLED).
 * - READY status is never automatically downgraded (user explicitly set it).
 *   Only automatic sync can move NOT_INSTALLED ↔ INSTALLED.
 */
class ProgressRepository(application: Application) {

    private val dataStore = application.progressDataStore

    // ─── Key helpers ─────────────────────────────────────────────────────────

    private fun keyFor(appId: String) = stringPreferencesKey("status_$appId")

    // ─── Read ─────────────────────────────────────────────────────────────────

    /**
     * Observes the status of a single app as a [Flow].
     * Emits immediately with the current value, then on every update.
     */
    fun getStatus(appId: String): Flow<AppStatus> =
        dataStore.data.map { prefs ->
            AppStatus.fromString(prefs[keyFor(appId)])
        }

    /**
     * Returns the current status of a single app without subscribing to updates.
     * Use in coroutines where you need a one-shot read (e.g., sync logic).
     */
    suspend fun getStatusOnce(appId: String): AppStatus =
        AppStatus.fromString(dataStore.data.first()[keyFor(appId)])

    /**
     * Returns a [Flow] of all app statuses as a map: appId → AppStatus.
     * Used by the HomeViewModel to compute overall progress.
     */
    fun getAllStatuses(): Flow<Map<String, AppStatus>> =
        dataStore.data.map { prefs ->
            prefs.asMap()
                .filter { it.key.name.startsWith("status_") }
                .mapKeys { it.key.name.removePrefix("status_") }
                .mapValues { AppStatus.fromString(it.value as? String) }
        }

    // ─── Write ────────────────────────────────────────────────────────────────

    /**
     * Persists the [status] for [appId].
     * Call from a coroutine context (suspend).
     */
    suspend fun setStatus(appId: String, status: AppStatus) {
        dataStore.edit { prefs ->
            prefs[keyFor(appId)] = status.name
        }
    }

    /**
     * Automatically syncs installation status from PackageManager results.
     *
     * Rules:
     * - NOT_INSTALLED → INSTALLED if package is now detected
     * - INSTALLED → NOT_INSTALLED if package was removed
     * - READY is never auto-downgraded (user manually set it)
     */
    suspend fun syncFromPackageManager(
        appId: String,
        isPackageInstalled: Boolean
    ) {
        val current = getStatusOnce(appId)
        val next = when {
            isPackageInstalled && current == AppStatus.NOT_INSTALLED -> AppStatus.INSTALLED
            !isPackageInstalled && current == AppStatus.INSTALLED -> AppStatus.NOT_INSTALLED
            else -> return // no change needed (also preserves READY)
        }
        setStatus(appId, next)
    }

    /**
     * Resets all app statuses to NOT_INSTALLED.
     * Called from Settings → Reset progress.
     */
    suspend fun resetAllProgress() {
        dataStore.edit { it.clear() }
    }
}
