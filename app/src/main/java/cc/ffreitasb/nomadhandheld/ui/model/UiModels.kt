package cc.ffreitasb.nomadhandheld.ui.model

import cc.ffreitasb.nomadhandheld.data.model.AppEntry
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.data.model.Category
import cc.ffreitasb.nomadhandheld.data.repository.ProgressCalculator

/**
 * An [AppEntry] paired with its current installation status.
 * This is the fundamental unit of data for all UI screens.
 */
data class AppWithStatus(
    val app: AppEntry,
    val status: AppStatus
) {
    val isActionable: Boolean get() = true // always shows a button

    /** True when the app is ready to be used (no setup remaining from the user's perspective). */
    val isComplete: Boolean get() = status == AppStatus.READY
}

/**
 * UI state for the Home/Dashboard screen.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        /** Apps grouped by category, in the fixed category display order. */
        val categories: List<Pair<Category, List<AppWithStatus>>>,
        /** Overall kit progress snapshot. */
        val progress: ProgressCalculator.ProgressSnapshot,
        /** Critical apps only, for the Field Sheet. */
        val criticalApps: List<AppWithStatus>
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}

/**
 * UI state for a single category screen.
 */
data class CategoryUiState(
    val category: Category,
    val apps: List<AppWithStatus>
)

/**
 * UI state for a single app card detail screen.
 */
data class CardDetailUiState(
    val app: AppWithStatus,
    /** Markdown content from the bundled onboarding guide. Null if file missing. */
    val onboardingMarkdown: String?,
)
