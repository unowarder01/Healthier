package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.OnboardingContract.Listener
import ui.OnboardingContract.State
import ui.content.OnboardingData
import unowarder01.healthier.core.designsystem.components.button.AppButton
import unowarder01.healthier.core.designsystem.components.button.AppButtonState
import unowarder01.healthier.core.designsystem.components.image.AppLogo

@Composable
fun OnboardingMainScreen(
    state: State,
    listener: Listener
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.items.size }
    )
    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FA))
            .systemBarsPadding()
    ) {
        Toolbar(
            currentPage = pagerState.currentPage,
            totalPagesCount = state.items.size
        )
        ViewPagerContent(
            pagerState = pagerState,
            items = state.items
        )
        Buttons(
            item = state.items[pagerState.currentPage],
            listener = listener
        )
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    currentPage: Int,
    totalPagesCount: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        AppLogo(
            shape = shapes.small,
            modifier = Modifier.size(40.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${currentPage + 1} / $totalPagesCount",
            style = typography.bodyMedium,
            color = Color(0xFF626272)
        )
    }
}

/**
 * VIEW PAGER CONTENT
 */
@Composable
private fun ColumnScope.ViewPagerContent(
    pagerState: PagerState,
    items: List<OnboardingData>
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier.weight(1f),
        pageContent = { page ->
            val item = items[page]
            Column(modifier = Modifier.fillMaxSize()) {
                ContentContainer()
                Title(item.title)
                Description(item.description)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}

@Composable
private fun ContentContainer() {
    Box(
        modifier = Modifier
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(334.dp)
            .clip(shapes.extraLarge)
            .background(
                color = Color(0xFFECE9FF),
                shape = shapes.extraLarge
            )
    )
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        style = typography.headlineMedium,
        color = Color(0xFF17171B),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)
    )
}

@Composable
private fun Description(text: String) {
    Text(
        text = text,
        style = typography.bodyLarge,
        color = Color(0xFF626272),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
    )
}

/**
 * BUTTONS
 */
@Composable
private fun Buttons(
    item: OnboardingData,
    listener: Listener
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        AppButton(
            text = item.positiveButtonText,
            style = typography.bodyLarge,
            state = AppButtonState.BlackWithWhiteText,
            onClick = { listener.onPositiveButtonClick() },
            modifier = Modifier.fillMaxWidth()
        )
        AppButton(
            text = item.negativeButtonText,
            state = AppButtonState.TransparentWithPurpleText,
            onClick = { listener.onNegativeButtonClick() },
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
