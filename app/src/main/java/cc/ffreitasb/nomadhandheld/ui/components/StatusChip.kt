package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.ffreitasb.nomadhandheld.data.model.AppStatus
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGray
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGrayContainer
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGreen
import cc.ffreitasb.nomadhandheld.ui.theme.StatusGreenContainer
import cc.ffreitasb.nomadhandheld.ui.theme.StatusYellow
import cc.ffreitasb.nomadhandheld.ui.theme.StatusYellowContainer

/**
 * Pill-shaped chip indicating the installation/configuration status of an app.
 *
 * NOT_INSTALLED → gray, "Não instalado"
 * INSTALLED     → yellow, "Instalado"
 * READY         → green, "Pronto ✓"
 */
@Composable
fun StatusChip(
    status: AppStatus,
    modifier: Modifier = Modifier
) {
    val (bg, fg, label, icon) = statusVisuals(status)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = fg,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = label,
            color = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.2.sp
        )
    }
}

private data class StatusVisuals(
    val bg: Color,
    val fg: Color,
    val label: String,
    val icon: ImageVector?
)

private fun statusVisuals(status: AppStatus): StatusVisuals = when (status) {
    AppStatus.NOT_INSTALLED -> StatusVisuals(
        bg = StatusGrayContainer,
        fg = StatusGray,
        label = "Não instalado",
        icon = null
    )
    AppStatus.INSTALLED -> StatusVisuals(
        bg = StatusYellowContainer,
        fg = StatusYellow,
        label = "Instalado",
        icon = null
    )
    AppStatus.READY -> StatusVisuals(
        bg = StatusGreenContainer,
        fg = StatusGreen,
        label = "Pronto",
        icon = null
    )
}
