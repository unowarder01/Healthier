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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.core.platform.MapAvailability

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapMainScreen(
    component: MapComponent,
    language: AppLanguage,
) = with(component.store) {
    val state by subscribe()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("map_screen"),
        contentAlignment = Alignment.Center,
    ) {
        when (component.availability) {
            MapAvailability.Unavailable ->
                Text(appString(language, TextKey.MapUnavailable), Modifier.testTag("map_unavailable"))

            MapAvailability.Demo,
            MapAvailability.Native -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        appString(language, TextKey.DemoMap),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    state.clinics.forEachIndexed { index, clinic ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.82f)
                                .clickable {
                                    intent(MapContract.Intent.SelectClinic(clinic.id))
                                }
                                .testTag("map_marker_${clinic.id}"),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                Modifier
                                    .size(42.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                                contentAlignment = Alignment.Center,
                            ) { Text("${index + 1}", color = MaterialTheme.colorScheme.onPrimary) }
                            Text(clinic.name, Modifier.padding(12.dp))
                        }
                    }
                }
            }
        }

        state.selectedClinic?.let { clinic ->
            ModalBottomSheet(
                onDismissRequest = { intent(MapContract.Intent.DismissClinic) },
                modifier = Modifier.testTag("clinic_bottom_sheet"),
            ) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    AsyncImage(
                        model = clinic.imageUrl,
                        contentDescription = clinic.name,
                        modifier = Modifier.fillMaxWidth().height(140.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                    )
                    Text(clinic.name, style = MaterialTheme.typography.headlineSmall)
                    Text(clinic.specialization)
                    Text(clinic.address)
                    Button(
                        onClick = { intent(MapContract.Intent.DismissClinic) },
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    ) { Text(appString(language, TextKey.Close)) }
                }
            }
        }
    }
}
