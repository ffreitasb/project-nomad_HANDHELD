package cc.ffreitasb.nomadhandheld.data.model

/**
 * Installation / configuration status for each app card.
 * Persisted in DataStore by app ID.
 *
 * NOT_INSTALLED → detected automatically via PackageManager
 * INSTALLED     → PackageManager confirms the app is present, but user hasn't marked it ready
 * READY         → user manually toggled "marked as ready" (we can't detect internal config)
 */
enum class AppStatus {
    NOT_INSTALLED,
    INSTALLED,
    READY;

    companion object {
        fun fromString(value: String?): AppStatus =
            entries.find { it.name == value } ?: NOT_INSTALLED
    }
}
