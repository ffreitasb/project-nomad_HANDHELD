package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

/**
 * Settings screen — Fase 6 implementation target.
 * Reset progress, About / credits (must credit Project NOMAD per PRD section 11), app version.
 */
@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO (Fase 6): reset DataStore via homeViewModel.resetAllProgress(), show version, credits to Project NOMAD
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Configurações",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
