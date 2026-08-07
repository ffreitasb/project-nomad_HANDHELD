package cc.ffreitasb.nomadhandheld.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * NOMAD:HANDHELD uses a dark-only theme.
 * "Field Sheet" principle: readability over decoration.
 * Light theme is intentionally not implemented in v1.
 */
private val NomadDarkColorScheme = darkColorScheme(
    primary = NomadAmber,
    onPrimary = NomadOnAmber,
    primaryContainer = NomadAmberContainer,
    onPrimaryContainer = NomadAmber,
    secondary = StatusGreen,
    onSecondary = NomadBlack,
    secondaryContainer = StatusGreenContainer,
    onSecondaryContainer = StatusGreen,
    tertiary = StatusYellow,
    onTertiary = NomadBlack,
    tertiaryContainer = StatusYellowContainer,
    onTertiaryContainer = StatusYellow,
    error = PriorityRed,
    errorContainer = PriorityRedContainer,
    onError = NomadBlack,
    onErrorContainer = PriorityRed,
    background = NomadBlack,
    onBackground = NomadOnSurface,
    surface = NomadSurface,
    onSurface = NomadOnSurface,
    surfaceVariant = NomadSurfaceVariant,
    onSurfaceVariant = NomadOnSurfaceVariant,
    outline = NomadOutline,
    outlineVariant = NomadOutline,
)

@Composable
fun NomadHandheldTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NomadDarkColorScheme,
        typography = NomadTypography,
        content = content
    )
}
