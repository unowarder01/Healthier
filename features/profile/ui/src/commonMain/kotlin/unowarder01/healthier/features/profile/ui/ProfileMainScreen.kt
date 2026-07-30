package unowarder01.healthier.features.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileMainScreen(component: ProfileComponent) = with(component.store) {
    val state by subscribe()
    val locationDescription = appString(state.language, TextKey.ChangeLocation)
    val languageDescription = appString(state.language, TextKey.ChangeLanguage)
    val editDescription = appString(state.language, TextKey.EditProfile)
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    appString(state.language, TextKey.Profile),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f),
                )
                IconButton(
                    onClick = component.navigator::changeLocation,
                    modifier = Modifier
                        .testTag("profile_change_location")
                        .semantics {
                            contentDescription = locationDescription
                        },
                ) { Text("⌖") }
                IconButton(
                    onClick = { intent(ProfileContract.Intent.ShowLanguageSelector) },
                    modifier = Modifier
                        .testTag("profile_change_language")
                        .semantics {
                            contentDescription = languageDescription
                        },
                ) { Text("文") }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).testTag("account_card"),
                shape = RoundedCornerShape(HealthierTokens.radius),
                elevation = CardDefaults.cardElevation(HealthierTokens.floatingElevation),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.profile.avatarReference == null) {
                        Box(
                            Modifier.size(108.dp).background(
                                MaterialTheme.colorScheme.primaryContainer,
                                CircleShape,
                            ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(state.profile.name.take(1).uppercase(), style = MaterialTheme.typography.headlineLarge)
                        }
                    } else {
                        AsyncImage(
                            model = state.profile.avatarReference,
                            contentDescription = state.profile.name,
                            modifier = Modifier.size(108.dp).clip(CircleShape),
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(state.profile.name, style = MaterialTheme.typography.titleLarge)
                        IconButton(
                            onClick = { intent(ProfileContract.Intent.StartEdit) },
                            modifier = Modifier
                                .testTag("edit_profile")
                                .semantics {
                                    contentDescription = editDescription
                                },
                        ) { Text("✎") }
                    }
                }
            }
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Documents),
                tag = "documents_section",
                rows = listOf(
                    "▣" to appString(state.language, TextKey.Identity),
                    "▣" to appString(state.language, TextKey.Consents),
                    "▣" to appString(state.language, TextKey.Scans),
                ),
                onClick = {
                    intent(ProfileContract.Intent.ShowMessage(ProfileContract.Message.ComingSoon))
                },
            )
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Settings),
                tag = "settings_section",
                rows = listOf(
                    "" to appString(state.language, TextKey.ColorTheme),
                    "" to appString(state.language, TextKey.AppLanguage),
                    "" to appString(state.language, TextKey.Notifications),
                ),
                onClick = { index ->
                    when (index) {
                        0 -> intent(ProfileContract.Intent.ShowThemeSelector)
                        1 -> intent(ProfileContract.Intent.ShowLanguageSelector)
                        else -> intent(ProfileContract.Intent.ShowMessage(ProfileContract.Message.NotConfigured))
                    }
                },
            )
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Social),
                tag = "social_section",
                rows = listOf(
                    "◉" to "Telegram",
                    "◉" to "WhatsApp",
                    "◉" to "Instagram",
                    "◉" to "Facebook",
                ),
                onClick = {
                    intent(ProfileContract.Intent.ShowMessage(ProfileContract.Message.NotConfigured))
                },
            )
            Spacer(Modifier.height(16.dp))
        }
    }

    if (state.editing) {
        ModalBottomSheet(
            onDismissRequest = { intent(ProfileContract.Intent.DismissEdit) },
            modifier = Modifier.testTag("profile_edit_sheet"),
        ) {
            Column(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(appString(state.language, TextKey.EditProfile), style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(
                    value = state.draftName,
                    onValueChange = { intent(ProfileContract.Intent.NameChanged(it)) },
                    label = { Text(appString(state.language, TextKey.Name)) },
                    modifier = Modifier.fillMaxWidth().testTag("profile_name"),
                )
                TextButton(
                    onClick = { intent(ProfileContract.Intent.PickAvatar) },
                    modifier = Modifier.testTag("profile_pick_avatar"),
                ) { Text(appString(state.language, TextKey.Avatar)) }
                Button(
                    onClick = { intent(ProfileContract.Intent.SaveProfile) },
                    modifier = Modifier.fillMaxWidth().testTag("profile_save"),
                ) { Text(appString(state.language, TextKey.Save)) }
            }
        }
    }

    if (state.showLanguageSelector) {
        ChoiceDialog(
            title = appString(state.language, TextKey.AppLanguage),
            values = listOf(
                appString(state.language, TextKey.LanguageGeorgian),
                appString(state.language, TextKey.LanguageEnglish),
                appString(state.language, TextKey.LanguageRussian),
            ),
            onDismiss = { intent(ProfileContract.Intent.DismissOverlay) },
            onSelect = { index ->
                intent(ProfileContract.Intent.SelectLanguage(AppLanguage.entries[index]))
            },
        )
    }

    if (state.showThemeSelector) {
        ChoiceDialog(
            title = appString(state.language, TextKey.ColorTheme),
            values = listOf(
                appString(state.language, TextKey.ThemeSystem),
                appString(state.language, TextKey.ThemeLight),
                appString(state.language, TextKey.ThemeDark),
            ),
            onDismiss = { intent(ProfileContract.Intent.DismissOverlay) },
            onSelect = { index ->
                intent(ProfileContract.Intent.SelectTheme(AppTheme.entries[index]))
            },
        )
    }

    state.message?.let { message ->
        AlertDialog(
            onDismissRequest = { intent(ProfileContract.Intent.DismissOverlay) },
            confirmButton = {
                TextButton(onClick = { intent(ProfileContract.Intent.DismissOverlay) }) {
                    Text(appString(state.language, TextKey.Close))
                }
            },
            text = {
                Text(
                    appString(
                        state.language,
                        if (message == ProfileContract.Message.ComingSoon) {
                            TextKey.ComingSoon
                        } else {
                            TextKey.NotConfigured
                        }
                    )
                )
            },
        )
    }
}

@Composable
private fun ProfileSection(
    title: String,
    tag: String,
    rows: List<Pair<String, String>>,
    onClick: (Int) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp).testTag(tag)) {
        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        Card(
            shape = RoundedCornerShape(HealthierTokens.radius),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            rows.forEachIndexed { index, row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(index) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (row.first.isNotBlank()) Text(row.first)
                    Text(row.second, modifier = Modifier.weight(1f))
                    Text("›")
                }
            }
        }
    }
}

@Composable
private fun ChoiceDialog(
    title: String,
    values: List<String>,
    onDismiss: () -> Unit,
    onSelect: (Int) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text(title) },
        text = {
            Column {
                values.forEachIndexed { index, value ->
                    TextButton(onClick = { onSelect(index) }, modifier = Modifier.fillMaxWidth()) {
                        Text(value)
                    }
                }
            }
        },
    )
}
