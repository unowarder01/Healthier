package unowarder01.healthier.features.city.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.features.city.domain.City
import unowarder01.healthier.features.city.ui.ChooseCityContract.Listener

@Composable
fun ChooseCityMainScreen(
    state: ChooseCityContract.State,
    listener: Listener,
    language: AppLanguage
) {
    LaunchedEffect(Unit) { listener.onScreenShown() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = appString(language, TextKey.YourCity),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    start = HealthierTokens.pageHorizontalPadding,
                    top = 28.dp,
                    end = HealthierTokens.pageHorizontalPadding,
                    bottom = 16.dp
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = HealthierTokens.pageHorizontalPadding,
                        end = HealthierTokens.pageHorizontalPadding
                    )
            ) {
                if (state.cities.isEmpty()) {
                    Text(
                        text = appString(language, TextKey.NoResults),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 112.dp)
                            .testTag("city_empty")
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("city_list"),
                        contentPadding = PaddingValues(bottom = 128.dp)
                    ) {
                        items(
                            items = state.cities,
                            key = City::id
                        ) { city ->
                            CityRow(
                                city = city,
                                language = language,
                                loading = state.loadingCityId == city.id,
                                enabled = state.loadingCityId == null,
                                onClick = { listener.onCitySelected(city.id) }
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = HealthierTokens.floatingElevation
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
                .navigationBarsPadding()
                .imePadding()
                .testTag("city_search_container")
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = listener::onQueryChanged,
                placeholder = { Text(appString(language, TextKey.Search)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .testTag("city_search")
            )
        }
    }
}

@Composable
private fun CityRow(
    city: City,
    language: AppLanguage,
    loading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 4.dp, vertical = 14.dp)
            .testTag("city_${city.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city.name(language),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(22.dp)
                    .testTag("city_progress_${city.id}"),
                strokeWidth = 2.dp
            )
        }
    }
}
