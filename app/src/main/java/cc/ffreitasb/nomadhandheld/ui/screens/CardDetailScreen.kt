package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.ui.components.PriorityBadge
import cc.ffreitasb.nomadhandheld.ui.components.SimpleMarkdown
import cc.ffreitasb.nomadhandheld.ui.components.StatusChip
import cc.ffreitasb.nomadhandheld.ui.model.CardDetailUiState
import cc.ffreitasb.nomadhandheld.ui.util.IntentUtils
import cc.ffreitasb.nomadhandheld.ui.viewmodel.CardDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(
    viewModel: CardDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState?.app?.entry?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        val state = uiState
        if (state == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Header tags
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PriorityBadge(priority = state.app.entry.priority)
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusChip(status = state.app.status)
                }

                // Markdown Content
                if (state.onboardingMarkdown != null) {
                    SimpleMarkdown(
                        content = state.onboardingMarkdown,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        "Guia não encontrado.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Recommended Content Section
                val recommended = state.app.entry.recommendedContent
                if (recommended.isNotEmpty()) {
                    Text(
                        text = "Conteúdo Recomendado",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    recommended.forEach { item ->
                        Row(modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)) {
                            Text(
                                text = "•",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Actions
                Text(
                    text = "Ações",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ActionButtons(
                    state = state,
                    onOpenApp = { IntentUtils.openApp(context, state.app.entry.packageName) },
                    onOpenStore = { IntentUtils.openStore(context, state.app.entry.packageName, state.app.entry.fdroidUrl) },
                    onMarkReady = { viewModel.markAsReady() },
                    onUnmarkReady = { viewModel.unmarkReady() }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun ActionButtons(
    state: CardDetailUiState,
    onOpenApp: () -> Unit,
    onOpenStore: () -> Unit,
    onMarkReady: () -> Unit,
    onUnmarkReady: () -> Unit
) {
    val status = state.app.status

    Column(modifier = Modifier.fillMaxWidth()) {
        when (status) {
            AppStatus.NOT_INSTALLED -> {
                Button(
                    onClick = onOpenStore,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.Shop, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Obter Aplicativo")
                }
            }
            AppStatus.INSTALLED -> {
                Button(
                    onClick = onOpenApp,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir Aplicativo")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onMarkReady,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Circle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Marcar como Pronto")
                }
            }
            AppStatus.READY -> {
                Button(
                    onClick = onOpenApp,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir Aplicativo")
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onUnmarkReady,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pronto (Desmarcar)")
                }
            }
        }
    }
}
