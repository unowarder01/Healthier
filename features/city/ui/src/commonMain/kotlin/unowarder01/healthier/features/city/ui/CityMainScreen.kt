package unowarder01.healthier.features.city.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.core.designsystem.components.text_field.AppTextField
import unowarder01.healthier.core.designsystem.extensions.clearFocusOnTap
import unowarder01.healthier.core.designsystem.extensions.outerShadow
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.ic_arrow_right
import unowarder01.healthier.designsystem.generated.resources.ic_location_pin
import unowarder01.healthier.designsystem.generated.resources.ic_search
import unowarder01.healthier.features.city.ui.CityContract.Listener
import unowarder01.healthier.features.city.ui.CityContract.State
import unowarder01.healthier.features.city.ui.data.CityUi
import unowarder01.healthier.features.city.ui.data.CityUi.ReadyCityUi
import unowarder01.healthier.features.city.ui.data.CityUi.SoonCityUi

@Composable
fun CityMainScreen(
    state: State,
    listener: Listener
) {
    val listState = rememberLazyListState()
    val showHeaderShadow by remember {
        derivedStateOf { listState.canScrollBackward }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .clearFocusOnTap()
            .systemBarsPadding()
    ) {
        stickyHeader {
            TitleAndSearchHeader(showShadow = showHeaderShadow)
        }
        cities(
            state = state,
            listener = listener
        )
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

/**
 * TITLE AND SEARCH STICKY HEADER
 */
@Composable
private fun TitleAndSearchHeader(showShadow: Boolean) {
    val shape = remember {
        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    }
    val shadowColor by animateColorAsState(
        animationSpec = tween(),
        targetValue = if (showShadow) {
            colorScheme.primary.copy(alpha = 0.1f)
        } else {
            Color.Transparent
        },
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .outerShadow(
                radius = 4.dp,
                spread = 4.dp,
                shape = shape,
                color = shadowColor
            )
            .clip(shape)
            .background(
                color = colorScheme.background,
                shape = shape
            )
            .padding(horizontal = 16.dp)
    ) {
        Title()
        Search()
    }
}

/**
 * TITLE
 */
@Composable
private fun Title() {
    Text(
        text = "Выберите Ваш город",
        color = colorScheme.primary,
        style = typography.displaySmall,
        modifier = Modifier.padding(top = 16.dp)
    )
}

/**
 * SEARCH
 */
@Composable
private fun Search() {
    AppTextField(
        value = "",
        onValueChange = {},
        placeholder = "Search",
        leadingIcon = Res.drawable.ic_search,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

/**
 * HEADER
 */
@Composable
private fun Header(text: StringResource) {
    Text(
        text = stringResource(text).uppercase(),
        color = colorScheme.onSurfaceVariant,
        style = typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
    )
}

/**
 * CITIES
 */
private fun LazyListScope.cities(
    state: State,
    listener: Listener
) {
    state.citiesUi.forEach { cityData ->
        item { Header(cityData.header) }
        items(cityData.items) { city ->
            City(
                city = city,
                onClick = { listener.onCityClick() }
            )
        }
    }
}

@Composable
private fun City(
    city: CityUi,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .height(74.dp)
            .clip(shapes.large)
            .background(
                color = colorScheme.onPrimary,
                shape = shapes.large
            )
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = shapes.large
            )
            .clickable(
                enabled = city is ReadyCityUi,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(shapes.medium)
                .background(
                    color = colorScheme.primaryContainer,
                    shape = shapes.medium
                )
        ) {
            AppImage(
                image = Res.drawable.ic_location_pin,
                color = colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = city.name,
                color = colorScheme.primary,
                style = typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            if (city is ReadyCityUi) {
                Text(
                    text = "${city.doctorsCount} докторов - ${city.clinicsCount} клиник",
                    color = colorScheme.onSurfaceVariant,
                    style = typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        if (city is SoonCityUi) {
            Text(
                text = "Soon",
                style = typography.labelSmall,
                color = colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(shapes.small)
                    .background(
                        color = colorScheme.secondary,
                        shape = shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
            AppImage(
                image = Res.drawable.ic_arrow_right,
                color = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
