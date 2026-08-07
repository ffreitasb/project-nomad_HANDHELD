package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.ui.model.AppWithStatus
import cc.ffreitasb.nomadhandheld.ui.util.launchApp
import cc.ffreitasb.nomadhandheld.ui.util.openStorePage

/**
 * App card used in both Home category previews and the Category detail screen.
 *
 * Behavior by status:
 * - NOT_INSTALLED → primary button "Instalar" → opens store
 * - INSTALLED     → outline button "Abrir" + secondary "Marcar como pronto"
 * - READY         → outline button "Abrir" (muted, green accent)
 *
 * Clicking the card body navigates to the CardDetail screen.
 */
@Composable
fun AppCard(
    appWithStatus: AppWithStatus,
    onCardClick: () -> Unit,
    onMarkReady: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = appWithStatus.app
    val status = appWithStatus.status

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onCardClick)
            .semantics { contentDescription = "${app.name}, ${statusLabel(status)}" },
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // ── Header row: name + priority badge ────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                PriorityBadge(priority = app.priority)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // ── Description ──────────────────────────────────────────────────
            Text(
                text = app.descriptionShort,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Footer row: status chip + action button ───────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status = status)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    when (status) {
                        AppStatus.NOT_INSTALLED -> {
                            Button(
                                onClick = {
                                    openStorePage(context, app.packageName, app.storeUrl)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("Instalar", style = MaterialTheme.typography.labelMedium)
                            }
                        }

                        AppStatus.INSTALLED -> {
                            OutlinedButton(
                                onClick = { launchApp(context, app.packageName) },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                )
                            ) {
                                Text("Abrir", style = MaterialTheme.typography.labelMedium)
                            }
                            Button(
                                onClick = onMarkReady,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                )
                            ) {
                                Text("Pronto ✓", style = MaterialTheme.typography.labelMedium)
                            }
                        }

                        AppStatus.READY -> {
                            OutlinedButton(
                                onClick = { launchApp(context, app.packageName) },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text("Abrir", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun statusLabel(status: AppStatus) = when (status) {
    AppStatus.NOT_INSTALLED -> "não instalado"
    AppStatus.INSTALLED -> "instalado"
    AppStatus.READY -> "pronto"
}
