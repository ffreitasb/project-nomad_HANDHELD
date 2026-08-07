package cc.ffreitasb.nomadhandheld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

/**
 * A highly simplified Markdown renderer tailored specifically for the
 * onboarding guides in NOMAD:HANDHELD.
 *
 * Supports: H1, H2, Bold, Inline Code, Bullet Lists, Ordered Lists, and Simple Tables.
 * Prevents adding heavy 3rd-party dependencies that might break Compose updates.
 */
@Composable
fun SimpleMarkdown(
    content: String,
    modifier: Modifier = Modifier
) {
    val blocks = content.split("\n\n").filter { it.isNotBlank() }

    Column(modifier = modifier) {
        blocks.forEach { block ->
            when {
                block.startsWith("# ") -> MarkdownH1(block.removePrefix("# "))
                block.startsWith("## ") -> MarkdownH2(block.removePrefix("## "))
                block.startsWith("> ") -> MarkdownQuote(block.removePrefix("> "))
                block.startsWith("|") -> MarkdownTable(block)
                block.lines().all { it.trim().startsWith("- ") } -> MarkdownBulletList(block)
                block.lines().all { it.trim().matches(Regex("^\\d+\\..*")) } -> MarkdownOrderedList(block)
                else -> MarkdownBody(block)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MarkdownH1(text: String) {
    Text(
        text = parseInlineMarkdown(text),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun MarkdownH2(text: String) {
    Text(
        text = parseInlineMarkdown(text),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
private fun MarkdownQuote(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
            .padding(12.dp)
    ) {
        Text(
            text = parseInlineMarkdown(text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MarkdownBody(text: String) {
    Text(
        text = parseInlineMarkdown(text),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
    )
}

@Composable
private fun MarkdownBulletList(text: String) {
    Column {
        text.lines().forEach { line ->
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp, start = 4.dp)
                )
                Text(
                    text = parseInlineMarkdown(line.trim().removePrefix("- ")),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MarkdownOrderedList(text: String) {
    Column {
        text.lines().forEach { line ->
            val cleanLine = line.trim()
            val number = cleanLine.substringBefore(". ")
            val content = cleanLine.substringAfter(". ")
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "$number.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = parseInlineMarkdown(content),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MarkdownTable(text: String) {
    val lines = text.lines().filter { it.isNotBlank() }
    if (lines.size < 3) return // Invalid table

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
    ) {
        lines.forEachIndexed { index, line ->
            // Skip the separator line (e.g. |---|---|)
            if (index == 1 && line.contains("---")) return@forEachIndexed

            val isHeader = index == 0
            val cells = line.split("|").map { it.trim() }.filter { it.isNotEmpty() }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isHeader) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                cells.forEachIndexed { cellIndex, cell ->
                    Text(
                        text = parseInlineMarkdown(cell),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
                        color = if (isHeader) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(if (cellIndex == 0) 1.5f else 1f)
                    )
                }
            }
        }
    }
}

/**
 * Parses **bold** and `code` inline elements into an AnnotatedString.
 */
@Composable
private fun parseInlineMarkdown(text: String): AnnotatedString {
    val boldRegex = Regex("\\*\\*(.*?)\\*\\*")
    val codeRegex = Regex("`(.*?)`")
    
    return buildAnnotatedString {
        var currentIndex = 0
        
        // Very basic single-pass parse: we find all bold and code matches, sort them by start index
        val boldMatches = boldRegex.findAll(text).map { it to "bold" }
        val codeMatches = codeRegex.findAll(text).map { it to "code" }
        
        val allMatches = (boldMatches + codeMatches).sortedBy { it.first.range.first }.toList()
        
        for ((match, type) in allMatches) {
            // Ignore overlaps (simplified approach)
            if (match.range.first < currentIndex) continue
            
            // Append text before match
            append(text.substring(currentIndex, match.range.first))
            
            // Append styled match content
            if (type == "bold") {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)) {
                    append(match.groupValues[1])
                }
            } else if (type == "code") {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        background = MaterialTheme.colorScheme.surfaceVariant,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(match.groupValues[1])
                }
            }
            
            currentIndex = match.range.last + 1
        }
        
        // Append remaining text
        if (currentIndex < text.length) {
            append(text.substring(currentIndex))
        }
    }
}
