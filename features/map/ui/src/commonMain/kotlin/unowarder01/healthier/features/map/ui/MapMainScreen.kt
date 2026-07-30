package unowarder01.healthier.features.map.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.core.platform.MapAvailability
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.map.ui.MapContract.Listener

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapMainScreen(
    state: MapContract.State,
    availability: MapAvailability,
    listener: Listener,
    language: AppLanguage
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("map_screen")
    ) {
        when (availability) {
            MapAvailability.Unavailable -> MapUnavailable(language)
            MapAvailability.Demo,
            MapAvailability.Native -> DemoMap(
                clinics = state.clinics,
                language = language,
                onClinicSelected = listener::onClinicSelected
            )
        }

        state.selectedClinic?.let { clinic ->
            ClinicDetailsSheet(
                clinic = clinic,
                language = language,
                onDismiss = listener::onClinicDismissed
            )
        }
    }
}

@Composable
private fun MapUnavailable(language: AppLanguage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(HealthierTokens.pageHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Map,
            contentDescription = null,
            modifier = Modifier.size(44.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = appString(language, TextKey.MapUnavailable),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 16.dp)
                .testTag("map_unavailable")
        )
    }
}

@Composable
private fun DemoMap(
    clinics: List<Clinic>,
    language: AppLanguage,
    onClinicSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(HealthierTokens.pageHorizontalPadding)
    ) {
        Text(
            text = appString(language, TextKey.Map),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 28.dp)
        )
        Text(
            text = appString(language, TextKey.DemoMap),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
        )
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "·  ·  ·  ·  ·  ·  ·  ·  ·  ·\n\n  ·  ·  ·  ·  ·  ·  ·  ·\n\n·  ·  ·  ·  ·  ·  ·  ·  ·",
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentSize()
                )
                clinics.forEachIndexed { index, clinic ->
                    MapMarker(
                        clinic = clinic,
                        index = index,
                        modifier = Modifier
                            .align(markerAlignment(index))
                            .padding(18.dp),
                        onClick = { onClinicSelected(clinic.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MapMarker(
    clinic: Clinic,
    index: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("map_marker_${clinic.id}")
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (index + 1).toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = clinic.name,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(96.dp)
        )
    }
}

@Composable
private fun markerAlignment(index: Int): Alignment = when (index % 3) {
    0 -> Alignment.TopStart
    1 -> Alignment.CenterEnd
    else -> Alignment.BottomStart
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ClinicDetailsSheet(
    clinic: Clinic,
    language: AppLanguage,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("clinic_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = HealthierTokens.pageHorizontalPadding,
                    end = HealthierTokens.pageHorizontalPadding,
                    bottom = 24.dp
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = clinic.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = appString(language, TextKey.Close)
                    )
                }
            }
            AsyncImage(
                model = clinic.imageUrl,
                contentDescription = clinic.name,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp)
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            InfoRow(Icons.Default.LocationOn, clinic.address)
            Text(
                text = clinic.specialization,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
            )
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(appString(language, TextKey.Close))
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String
) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
