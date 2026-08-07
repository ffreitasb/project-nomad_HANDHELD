package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.data.model.Category
import cc.ffreitasb.nomadhandheld.ui.model.AppWithStatus
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGray
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGreen
import cc.ffreitasb.nomadhandheld.ui.theme.StatusYellow

/**
 * Category summary card shown on the Home screen.
 * Displays: category name, completion progress, mini app list preview, chevron to navigate.
 */
@Composable
fun CategoryCard(
    category: Category,
    apps: List<AppWithStatus>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val total = apps.size
    val installed = apps.count { it.status != AppStatus.NOT_INSTALLED }
    val ready = apps.count { it.status == AppStatus.READY }
    val progress = if (total > 0) installed.toFloat() / total else 0f

    val progressColor = when {
        ready == total && total > 0 -> StatusGreen
        installed > 0 -> StatusYellow
        else -> StatusGray
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .semantics {
                contentDescription = "${category.displayName}: $installed de $total apps instalados"
            },
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // ── Header: category name + count + chevron ───────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = progressLabel(installed, ready, total),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ver categoria",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ── Progress bar ─────────────────────────────────────────────────
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.outline
            )

            // ── App name chips preview (up to 3) ─────────────────────────────
            if (apps.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    apps.take(3).forEach { appWithStatus ->
                        AppNameChip(appWithStatus = appWithStatus)
                    }
                    if (apps.size > 3) {
                        Text(
                            text = "+${apps.size - 3}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

/** Mini inline chip just showing the app name with status color dot. */
@Composable
private fun AppNameChip(
    appWithStatus: AppWithStatus,
    modifier: Modifier = Modifier
) {
    val dotColor = when (appWithStatus.status) {
        AppStatus.NOT_INSTALLED -> StatusGray
        AppStatus.INSTALLED -> StatusYellow
        AppStatus.READY -> StatusGreen
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(6.dp),
            shape = RoundedCornerShape(3.dp),
            color = dotColor
        ) {}
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = appWithStatus.app.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

private fun progressLabel(installed: Int, ready: Int, total: Int): String = when {
    total == 0 -> "Sem apps"
    ready == total -> "Todos prontos ✓"
    installed == 0 -> "Nenhum instalado"
    else -> "$installed/$total instalados · $ready prontos"
}
