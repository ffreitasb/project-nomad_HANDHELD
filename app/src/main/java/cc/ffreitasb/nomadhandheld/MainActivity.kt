package cc.ffreitasb.nomadhandheld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cc.ffreitasb.nomadhandheld.ui.navigation.NomadNavHost
import cc.ffreitasb.nomadhandheld.ui.theme.NomadHandheldTheme
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    /**
     * ViewModel is scoped to the Activity lifecycle.
     * viewModels() delegate ensures it survives config changes (rotation).
     */
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NomadHandheldTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // innerPadding will be wired to the NavHost in Fase 3
                    // when the bottom navigation bar is added.
                    @Suppress("UNUSED_EXPRESSION")
                    innerPadding
                    NomadNavHost(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    /**
     * Triggered when the user returns to the app from another app (e.g., after
     * installing Kiwix from Play Store). Re-checks PackageManager so the status
     * chip updates immediately without requiring a full app restart.
     */
    override fun onResume() {
        super.onResume()
        homeViewModel.refreshInstallationStatus()
    }
}

