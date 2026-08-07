package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

/**
 * Home/Dashboard screen — Fase 3 implementation target.
 * Shows overall kit progress + categories accordion/grid.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCategoryClick: (categoryId: String) -> Unit,
    onFieldSheetClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO (Fase 3): implement ProgressBar + CategorySection components
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NOMAD:HANDHELD",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Kit de sobrevivência digital offline",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
