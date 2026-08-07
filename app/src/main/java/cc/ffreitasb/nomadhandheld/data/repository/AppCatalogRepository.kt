package cc.ffreitasb.nomadhandheld.data.repository

import android.content.Context
import android.util.Log
import cc.ffreitasb.nomadhandheld.data.model.AppEntry
import cc.ffreitasb.nomadhandheld.data.model.AppPriority
import kotlinx.serialization.json.Json

private const val TAG = "AppCatalogRepository"
private const val CATALOG_FILE = "curated_apps.json"

/**
 * Provides the curated app catalog from the static JSON bundled in assets/.
 *
 * Design decisions:
 * - No caching: the file is small (<50KB) and Kotlin's IO is fast enough.
 * - No Flow: this is read-once static data, not reactive.
 * - Context is injected so this can be unit tested with a fake context.
 * - ignoreUnknownKeys = true: forward-compatible if JSON gains new fields later.
 */
class AppCatalogRepository(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    /**
     * Returns the full curated app list, parsed from assets/curated_apps.json.
     * Throws [IllegalStateException] if the file is missing or malformed.
     */
    fun getApps(): List<AppEntry> {
        return try {
            context.assets.open(CATALOG_FILE).use { stream ->
                val text = stream.bufferedReader().readText()
                json.decodeFromString<List<AppEntry>>(text).also {
                    Log.d(TAG, "Loaded ${it.size} apps from catalog")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load catalog from assets/$CATALOG_FILE", e)
            throw IllegalStateException("Could not load app catalog: ${e.message}", e)
        }
    }

    /**
     * Returns all apps belonging to the given category ID.
     */
    fun getAppsByCategory(categoryId: String): List<AppEntry> =
        getApps().filter { it.category == categoryId }

    /**
     * Returns a single app by its unique ID, or null if not found.
     */
    fun getAppById(id: String): AppEntry? =
        getApps().find { it.id == id }

    /**
     * Returns only apps with [AppPriority.CRITICAL].
     * Used by the Field Sheet screen to show the most important apps.
     */
    fun getCriticalApps(): List<AppEntry> =
        getApps().filter { it.priority == AppPriority.CRITICAL }

    /**
     * Loads the onboarding markdown content for a given app.
     * Returns null if the file is missing (graceful degradation).
     */
    fun getOnboardingContent(app: AppEntry): String? {
        return try {
            context.assets.open(app.onboardingMdPath).use { stream ->
                stream.bufferedReader().readText()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Onboarding file not found for app '${app.id}': ${app.onboardingMdPath}")
            null
        }
    }
}
