package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.ffreitasb.nomadhandheld.data.repository.ProgressCalculator
import cc.ffreitasb.nomadhandheld.ui.theme.NomadAmber
import cc.ffreitasb.nomadhandheld.ui.theme.NomadAmberContainer
import cc.ffreitasb.nomadhandheld.ui.theme.PriorityRed
import cc.ffreitasb.nomadhandheld.ui.theme.PriorityRedContainer
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGreen
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGreenContainer
import cc.ffreitasb.nomadhandheld.ui.theme.StatusYellow

/**
 * Full-width header shown at the top of the Home screen.
 *
 * Displays:
 * - Main "Kit X% pronto" animated progress bar
 * - Critical apps readiness badge ("Apps críticos: X/Y")
 * - Subtle subtitle framing the purpose of the kit
 */
@Composable
fun KitProgressHeader(
    snapshot: ProgressCalculator.ProgressSnapshot,
    modifier: Modifier = Modifier
) {
    var animatedProgress by remember { mutableFloatStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(durationMillis = 800),
        label = "progress"
    )

    LaunchedEffect(snapshot.overallPercent) {
        animatedProgress = snapshot.overallPercent / 100f
    }

    val barColor = when {
        snapshot.overallPercent >= 80 -> StatusGreen
        snapshot.overallPercent >= 40 -> StatusYellow
        else -> NomadAmber
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // ── Title ─────────────────────────────────────────────────────────
            Text(
                text = "Kit de sobrevivência digital",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = snapshot.progressLabel(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = barColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Progress bar ─────────────────────────────────────────────────
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = barColor,
                trackColor = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ── Progress counts ───────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${snapshot.installedCount} de ${snapshot.totalCount} apps",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                CriticalBadge(
                    ready = snapshot.criticalReady,
                    total = snapshot.criticalTotal
                )
            }
        }
    }
}

/**
 * Small badge showing critical app readiness.
 * Turns green when all critical apps are ready.
 */
@Composable
private fun CriticalBadge(ready: Int, total: Int) {
    val isComplete = ready >= total && total > 0
    val bg = if (isComplete) StatusGreenContainer else PriorityRedContainer
    val fg = if (isComplete) StatusGreen else PriorityRed

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(fg, CircleShape)
        )
        Text(
            text = "Críticos: $ready/$total",
            color = fg,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
