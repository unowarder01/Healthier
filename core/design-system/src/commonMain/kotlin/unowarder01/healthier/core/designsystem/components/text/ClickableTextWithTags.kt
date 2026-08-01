package unowarder01.healthier.core.designsystem.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkAnnotation.Clickable
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign

private val XML_TAG_REGEX = "<(\\w+)>(.*?)</\\1>".toRegex()

@Composable
fun ClickableTextWithTags(
    fullText: String,
    tagHandlers: Map<String, () -> Unit>,
    tagTextOverrides: Map<String, String?> = emptyMap(),
    linkStyle: SpanStyle,
    textStyle: TextStyle,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedStringFromStringWithTags(
        fullText = fullText,
        tagHandlers = tagHandlers,
        tagTextOverrides = tagTextOverrides,
        linkStyle = linkStyle
    )
    Text(
        text = annotatedString,
        style = textStyle,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
private fun buildAnnotatedStringFromStringWithTags(
    fullText: String,
    tagHandlers: Map<String, () -> Unit>,
    tagTextOverrides: Map<String, String?> = emptyMap(),
    linkStyle: SpanStyle
): AnnotatedString = remember(fullText, tagHandlers, tagTextOverrides) {
    buildAnnotatedString {
        var lastIndex = 0
        val matches = XML_TAG_REGEX.findAll(fullText).toList()
        for (match in matches) {
            val (tag, linkText) = match.destructured
            append(fullText.substring(lastIndex, match.range.first))
            val resolvedLinkText = tagTextOverrides[tag] ?: linkText
            val start = length
            append(resolvedLinkText)
            addLink(
                start = start,
                end = start + resolvedLinkText.length,
                clickable = Clickable(
                    tag = tag,
                    styles = TextLinkStyles(
                        style = linkStyle,
                        focusedStyle = linkStyle,
                        pressedStyle = linkStyle,
                        hoveredStyle = linkStyle
                    ),
                    linkInteractionListener = { tagHandlers[tag]?.invoke() }
                )
            )
            lastIndex = match.range.last + 1
        }
        if (lastIndex < fullText.length) {
            append(fullText.substring(lastIndex))
        }
    }
}
