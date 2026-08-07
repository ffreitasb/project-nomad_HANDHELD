package cc.ffreitasb.nomadhandheld.ui.navigation

/**
 * Type-safe navigation destinations for NOMAD:HANDHELD.
 * All 5 screens from PRD section 7.
 */
sealed class Screen(val route: String) {
    /** Home/Dashboard — progress overview + category accordion */
    data object Home : Screen("home")

    /** Category detail — list of AppCards for one category */
    data class Category(val categoryId: String) : Screen("category/{categoryId}") {
        companion object {
            const val ROUTE = "category/{categoryId}"
            const val ARG_CATEGORY_ID = "categoryId"
            fun createRoute(categoryId: String) = "category/$categoryId"
        }
    }

    /** Card detail — expanded onboarding guide, recommended content, status toggle */
    data class CardDetail(val appId: String) : Screen("card/{appId}") {
        companion object {
            const val ROUTE = "card/{appId}"
            const val ARG_APP_ID = "appId"
            fun createRoute(appId: String) = "card/$appId"
        }
    }

    /** Field Sheet — compact emergency view of critical apps */
    data object FieldSheet : Screen("field_sheet")

    /** Settings — reset progress, about, credits */
    data object Settings : Screen("settings")
}
