package unowarder01.healthier.features.health.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString

@Composable
fun HealthMainScreen(
    component: HealthComponent,
    language: AppLanguage,
) = with(component.store) {
    val state by subscribe()
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("health_screen"),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            HealthToolbar(
                title = appString(language, TextKey.Health),
                locationDescription = appString(language, TextKey.ChangeLocation),
                languageDescription = appString(language, TextKey.ChangeLanguage),
                onLocation = component.navigator::changeLocation,
                onLanguage = component.navigator::changeLanguage,
            )
            OutlinedTextField(
                value = state.query,
                onValueChange = { intent(HealthContract.Intent.QueryChanged(it)) },
                placeholder = { Text(appString(language, TextKey.HealthSearch)) },
                singleLine = true,
                shape = RoundedCornerShape(HealthierTokens.radius),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("health_search"),
            )
        }
        item {
            SectionTitle(appString(language, TextKey.Stories))
            LazyRow(Modifier.fillMaxWidth().testTag("stories_list")) {
                item { Spacer(Modifier.width(16.dp)) }
                items(state.filtered.stories, key = { it.id }) { story ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(84.dp),
                    ) {
                        Box(
                            Modifier
                                .size(64.dp)
                                .border(2.dp, HealthierTokens.accent, CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                            contentAlignment = Alignment.Center,
                        ) { Text("H") }
                        Text(story.title, fontSize = 12.sp, maxLines = 1)
                    }
                }
                item { Spacer(Modifier.width(16.dp)) }
            }
        }
        item {
            SectionTitle(appString(language, TextKey.TopClinics))
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val cardWidth = (maxWidth - 32.dp - 12.dp) / 1.25f
                LazyRow(
                    modifier = Modifier.fillMaxWidth().testTag("clinics_list"),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item { Spacer(Modifier.width(4.dp)) }
                    items(state.filtered.clinics, key = { it.id }) { clinic ->
                        Card(
                            modifier = Modifier.width(cardWidth),
                            shape = RoundedCornerShape(HealthierTokens.radius),
                            elevation = CardDefaults.cardElevation(2.dp),
                        ) {
                            AsyncImage(
                                model = clinic.imageUrl,
                                contentDescription = clinic.name,
                                modifier = Modifier.fillMaxWidth().aspectRatio(1.65f)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                            )
                            Text(
                                clinic.name,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(12.dp),
                            )
                        }
                    }
                    item { Spacer(Modifier.width(4.dp)) }
                }
            }
        }
        item {
            SectionTitle(appString(language, TextKey.TopDoctors))
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val cardWidth = (maxWidth - 32.dp - 24.dp) / 2.25f
                LazyRow(
                    modifier = Modifier.fillMaxWidth().testTag("doctors_list"),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item { Spacer(Modifier.width(4.dp)) }
                    items(state.filtered.doctors, key = { it.id }) { doctor ->
                        Card(
                            modifier = Modifier.width(cardWidth),
                            shape = RoundedCornerShape(HealthierTokens.radius),
                        ) {
                            Box(
                                Modifier.fillMaxWidth().aspectRatio(1f)
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center,
                            ) { Text(doctor.name.take(1).uppercase()) }
                            Text(
                                doctor.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                    }
                    item { Spacer(Modifier.width(4.dp)) }
                }
            }
        }
    }
}

@Composable
private fun HealthToolbar(
    title: String,
    locationDescription: String,
    languageDescription: String,
    onLocation: () -> Unit,
    onLanguage: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = MaterialTheme.typography.headlineLarge, modifier = Modifier.weight(1f))
        IconButton(
            onClick = onLocation,
            modifier = Modifier
                .testTag("change_location")
                .semantics { contentDescription = locationDescription },
        ) { Text("⌖") }
        IconButton(
            onClick = onLanguage,
            modifier = Modifier
                .testTag("change_language")
                .semantics { contentDescription = languageDescription },
        ) { Text("文") }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
    )
}
