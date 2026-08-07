package cc.ffreitasb.nomadhandheld.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cc.ffreitasb.nomadhandheld.ui.screens.CardDetailScreen
import cc.ffreitasb.nomadhandheld.ui.screens.CategoryScreen
import cc.ffreitasb.nomadhandheld.ui.screens.FieldSheetScreen
import cc.ffreitasb.nomadhandheld.ui.screens.HomeScreen
import cc.ffreitasb.nomadhandheld.ui.screens.SettingsScreen
import cc.ffreitasb.nomadhandheld.ui.viewmodel.CardDetailViewModel
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

@Composable
fun NomadNavHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.Category.createRoute(categoryId))
                },
                onFieldSheetClick = {
                    navController.navigate(Screen.FieldSheet.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.Category.ROUTE,
            arguments = listOf(
                navArgument(Screen.Category.ARG_CATEGORY_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(Screen.Category.ARG_CATEGORY_ID) ?: ""
            CategoryScreen(
                categoryId = categoryId,
                homeViewModel = homeViewModel,
                onCardClick = { appId ->
                    navController.navigate(Screen.CardDetail.createRoute(appId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.CardDetail.ROUTE,
            arguments = listOf(
                navArgument(Screen.CardDetail.ARG_APP_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel: CardDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            CardDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FieldSheet.route) {
            FieldSheetScreen(
                homeViewModel = homeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                homeViewModel = homeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

