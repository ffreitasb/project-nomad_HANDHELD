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
 * Category screen — Fase 3 implementation target.
 * Shows all AppCards for the given category.
 */
@Composable
fun CategoryScreen(
    categoryId: String,
    homeViewModel: HomeViewModel,
    onCardClick: (appId: String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO (Fase 3): implement category header + app card list
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Categoria: $categoryId",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
