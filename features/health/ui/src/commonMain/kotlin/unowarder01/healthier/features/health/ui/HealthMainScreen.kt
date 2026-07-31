package unowarder01.healthier.features.health.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.components.image.AppLogo
import unowarder01.healthier.core.designsystem.theme.HealthierTokens
import unowarder01.healthier.core.designsystem.strings.TextKey
import unowarder01.healthier.core.designsystem.strings.appString
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.domain.Doctor
import unowarder01.healthier.features.health.domain.Story
import unowarder01.healthier.features.health.ui.HealthContract.Listener

@Composable
fun HealthMainScreen(
    state: HealthContract.State,
    listener: Listener,
    language: AppLanguage
) {
    LaunchedEffect(Unit) { listener.onScreenShown() }

    val isEmptySearch = state.query.trim().isNotEmpty() &&
        state.filtered.stories.isEmpty() &&
        state.filtered.clinics.isEmpty() &&
        state.filtered.doctors.isEmpty()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("health_screen"),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        item {
            HealthHeader(
                language = language,
                onLocationChange = listener::onLocationChangeRequested,
                onLanguageChange = listener::onLanguageChangeRequested
            )
            OutlinedTextField(
                value = state.query,
                onValueChange = listener::onQueryChanged,
                placeholder = { Text(appString(language, TextKey.HealthSearch)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HealthierTokens.pageHorizontalPadding)
                    .testTag("health_search")
            )
        }
        if (isEmptySearch) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .testTag("health_empty"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = appString(language, TextKey.NoResults),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item { SectionHeader(appString(language, TextKey.Stories)) }
            item {
                StoriesRow(
                    stories = state.filtered.stories,
                    modifier = Modifier.testTag("stories_list")
                )
            }
            item { SectionHeader(appString(language, TextKey.TopClinics)) }
            item {
                ClinicsRow(
                    clinics = state.filtered.clinics,
                    modifier = Modifier.testTag("clinics_list")
                )
            }
            item { SectionHeader(appString(language, TextKey.TopDoctors)) }
            item {
                DoctorsRow(
                    doctors = state.filtered.doctors,
                    modifier = Modifier.testTag("doctors_list")
                )
            }
        }
    }
}

@Composable
private fun HealthHeader(
    language: AppLanguage,
    onLocationChange: () -> Unit,
    onLanguageChange: () -> Unit
) {
    val changeLocationDescription = appString(language, TextKey.ChangeLocation)
    val changeLanguageDescription = appString(language, TextKey.ChangeLanguage)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = HealthierTokens.pageHorizontalPadding,
                top = 22.dp,
                end = 8.dp,
                bottom = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppLogo(
            modifier = Modifier.size(44.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = "Healthier",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = appString(language, TextKey.Health),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            onClick = onLocationChange,
            modifier = Modifier
                .testTag("change_location")
                .semantics {
                    contentDescription = changeLocationDescription
                }
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )
        }
        IconButton(
            onClick = onLanguageChange,
            modifier = Modifier
                .testTag("change_language")
                .semantics {
                    contentDescription = changeLanguageDescription
                }
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(
            start = HealthierTokens.pageHorizontalPadding,
            top = HealthierTokens.sectionSpacing,
            bottom = 12.dp
        )
    )
}

@Composable
private fun StoriesRow(
    stories: List<Story>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = HealthierTokens.pageHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stories, key = Story::id) { story ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(72.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = story.title.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = story.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun ClinicsRow(
    clinics: List<Clinic>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = HealthierTokens.pageHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(clinics, key = Clinic::id) { clinic ->
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.width(248.dp)
            ) {
                AsyncImage(
                    model = clinic.imageUrl,
                    contentDescription = clinic.name,
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                    error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.7f)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = clinic.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = clinic.specialization,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DoctorsRow(
    doctors: List<Doctor>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = HealthierTokens.pageHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(doctors, key = Doctor::id) { doctor ->
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.width(164.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.15f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = doctor.name.take(1).uppercase(),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = doctor.specialty,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
