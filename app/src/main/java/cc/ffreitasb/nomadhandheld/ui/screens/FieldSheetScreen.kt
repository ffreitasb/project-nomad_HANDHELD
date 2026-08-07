package cc.ffreitasb.nomadhandheld.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.ffreitasb.nomadhandheld.data.model.AppPriority
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.ui.model.HomeUiState
import cc.ffreitasb.nomadhandheld.ui.util.IntentUtils
import cc.ffreitasb.nomadhandheld.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldSheetScreen(
    homeViewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Brutalist styling for maximum contrast in emergency mode
    val brutalistBg = Color.Black
    val brutalistText = Color.White
    val brutalistAccent = Color(0xFFFFC107) // Amber/Warning color
    val brutalistError = Color(0xFFF44336)  // Red for missing critical apps

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = brutalistBg,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "FICHA DE CAMPO", 
                        fontWeight = FontWeight.Black, 
                        color = brutalistText,
                        letterSpacing = 2.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = brutalistText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brutalistBg,
                    titleContentColor = brutalistText,
                    navigationIconContentColor = brutalistText
                )
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                // Ignore, should be fast
            }
            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("ERRO AO CARREGAR DADOS", color = brutalistError, fontWeight = FontWeight.Bold)
                }
            }
            is HomeUiState.Success -> {
                val criticalApps = state.apps.filter { it.entry.priority == AppPriority.CRITICAL }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = brutalistAccent,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = "APPS CRÍTICOS PARA SOBREVIVÊNCIA",
                                color = brutalistAccent,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    items(criticalApps) { app ->
                        val isInstalled = app.status != AppStatus.NOT_INSTALLED
                        
                        Button(
                            onClick = {
                                if (isInstalled) {
                                    IntentUtils.openApp(context, app.entry.packageName)
                                } else {
                                    IntentUtils.openStore(context, app.entry.packageName, app.entry.fdroidUrl)
                                }
                            },
                            shape = MaterialTheme.shapes.small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isInstalled) brutalistText else Color.DarkGray,
                                contentColor = if (isInstalled) brutalistBg else brutalistText
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(88.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = app.entry.name.uppercase(),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 24.sp,
                                    letterSpacing = 1.sp,
                                    textAlign = TextAlign.Start
                                )
                                if (!isInstalled) {
                                    Text(
                                        text = "FALTA! TOQUE PARA BAIXAR",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = brutalistAccent,
                                        textAlign = TextAlign.Start
                                    )
                                } else if (app.status == AppStatus.READY) {
                                    Text(
                                        text = "PRONTO",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF4CAF50), // Green for ready in dark mode
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
