package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Card Detail screen — Fase 4 implementation target.
 * Shows expanded onboarding guide, recommended content, and status toggle.
 */
@Composable
fun CardDetailScreen(
    appId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO (Fase 4): render onboarding markdown, recommended content checklist, READY toggle
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "App: $appId",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
