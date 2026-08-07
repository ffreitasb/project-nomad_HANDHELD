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
 * Field Sheet screen — Fase 5 implementation target.
 * Compact emergency view: critical apps only, maximum contrast, no decoration.
 * PRD: "em uma mão, com a tela no brilho mínimo, conseguir ler e abrir os apps críticos"
 */
@Composable
fun FieldSheetScreen(
    homeViewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO (Fase 5): implement compact critical-apps list with direct deep links
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "⚠ Ficha de Campo",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
