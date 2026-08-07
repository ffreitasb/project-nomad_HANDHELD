package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.ffreitasb.nomadhandheld.data.model.AppPriority
import cc.ffreitasb.nomadhandheld.ui.theme.NomadAmber
import cc.ffreitasb.nomadhandheld.ui.theme.NomadAmberContainer
import cc.ffreitasb.nomadhandheld.ui.theme.NomadOnSurfaceVariant
import cc.ffreitasb.nomadhandheld.ui.theme.NomadSurfaceVariant
import cc.ffreitasb.nomadhandheld.ui.theme.PriorityRed
import cc.ffreitasb.nomadhandheld.ui.theme.PriorityRedContainer

/**
 * Small pill badge indicating an app's priority tier.
 *
 * CRITICAL → red, "CRÍTICO"
 * RECOMMENDED → amber, "RECOMENDADO"
 * OPTIONAL → gray (not rendered — optional apps don't need noise)
 */
@Composable
fun PriorityBadge(
    priority: AppPriority,
    modifier: Modifier = Modifier
) {
    if (priority == AppPriority.OPTIONAL) return // silent — don't clutter optional apps

    val (bgColor, textColor, label) = when (priority) {
        AppPriority.CRITICAL -> Triple(PriorityRedContainer, PriorityRed, "CRÍTICO")
        AppPriority.RECOMMENDED -> Triple(NomadAmberContainer, NomadAmber, "RECOMENDADO")
        AppPriority.OPTIONAL -> return
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
