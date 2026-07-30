package unowarder01.healthier.features.city.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString

@Composable
fun ChooseCityMainScreen(
    component: ChooseCityComponent,
    language: AppLanguage,
) = with(component.store) {
    val state by subscribe { component.handle(it) }
    LaunchedEffect(Unit) { intent(ChooseCityContract.Intent.Load) }

    Column(Modifier.fillMaxSize()) {
        Text(
            text = appString(language, TextKey.YourCity),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
        )
        Box(Modifier.fillMaxSize()) {
            if (state.cities.isEmpty()) {
                Text(
                    text = appString(language, TextKey.NoResults),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 104.dp)
                        .testTag("city_empty"),
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 116.dp),
                    modifier = Modifier.fillMaxSize().testTag("city_list"),
                ) {
                    items(state.cities, key = { it.id }) { city ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = state.loadingCityId != city.id) {
                                    intent(ChooseCityContract.Intent.SelectCity(city.id))
                                }
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                                .testTag("city_${city.id}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(city.name(language), modifier = Modifier.weight(1f))
                            when {
                                state.loadingCityId == city.id ->
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp).testTag("city_progress_${city.id}"),
                                        strokeWidth = 2.dp,
                                    )
                                state.errorCityId == city.id ->
                                    Text(
                                        appString(language, TextKey.TryAgain),
                                        color = MaterialTheme.colorScheme.error,
                                    )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .testTag("city_search_container"),
                shape = RoundedCornerShape(HealthierTokens.radius),
                elevation = CardDefaults.cardElevation(HealthierTokens.floatingElevation),
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { intent(ChooseCityContract.Intent.QueryChanged(it)) },
                    placeholder = { Text(appString(language, TextKey.Search)) },
                    singleLine = true,
                    shape = RoundedCornerShape(HealthierTokens.radius),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("city_search"),
                )
            }
        }
    }
}
