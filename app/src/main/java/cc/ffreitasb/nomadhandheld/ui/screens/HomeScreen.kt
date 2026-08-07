package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cc.ffreitasb.nomadhandheld.ui.components.CategoryCard
import cc.ffreitasb.nomadhandheld.ui.components.KitProgressHeader
import cc.ffreitasb.nomadhandheld.ui.model.HomeUiState
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCategoryClick: (categoryId: String) -> Unit,
    onFieldSheetClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "NOMAD:HANDHELD",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    // Field Sheet shortcut — always accessible
                    IconButton(onClick = onFieldSheetClick) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Ficha de Campo",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> LoadingState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )

            is HomeUiState.Error -> ErrorState(
                message = state.message,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )

            is HomeUiState.Success -> HomeContent(
                state = state,
                onCategoryClick = onCategoryClick,
                onMarkReady = viewModel::markAsReady,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

// ─── Content ──────────────────────────────────────────────────────────────────

@Composable
private fun HomeContent(
    state: HomeUiState.Success,
    onCategoryClick: (categoryId: String) -> Unit,
    onMarkReady: (appId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── Kit progress header ───────────────────────────────────────────────
        item(key = "progress_header") {
            KitProgressHeader(
                snapshot = state.progress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ── Category cards ────────────────────────────────────────────────────
        items(
            items = state.categories,
            key = { (category, _) -> category.id }
        ) { (category, apps) ->
            CategoryCard(
                category = category,
                apps = apps,
                onClick = { onCategoryClick(category.id) }
            )
        }
    }
}

// ─── Loading / Error states ───────────────────────────────────────────────────

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorState(message: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = "Erro ao carregar catálogo:\n$message",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(24.dp)
        )
    }
}
