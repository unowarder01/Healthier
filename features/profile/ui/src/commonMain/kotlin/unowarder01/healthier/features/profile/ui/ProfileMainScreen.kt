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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.designsystem.theme.HealthierTokens
import unowarder01.healthier.core.designsystem.strings.TextKey
import unowarder01.healthier.core.designsystem.strings.appString
import unowarder01.healthier.features.profile.ui.ProfileContract.Listener

@Composable
fun ProfileMainScreen(
    state: ProfileContract.State,
    listener: Listener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(HealthierTokens.sectionSpacing)
    ) {
        item {
            ProfileHeader(
                language = state.language,
                onLocationChange = listener::onLocationChangeRequested,
                onLanguageChange = listener::onLanguageSelectorRequested
            )
        }
        item {
            ProfileSummary(
                state = state,
                onEdit = listener::onEditingStarted
            )
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Documents),
                tag = "documents_section",
                rows = listOf(
                    ProfileRow(Icons.Default.Description, appString(state.language, TextKey.Identity)),
                    ProfileRow(Icons.Default.Description, appString(state.language, TextKey.Consents)),
                    ProfileRow(Icons.Default.Description, appString(state.language, TextKey.Scans))
                ),
                onClick = { listener.onComingSoonActionSelected() }
            )
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Settings),
                tag = "settings_section",
                rows = listOf(
                    ProfileRow(Icons.Default.Palette, appString(state.language, TextKey.ColorTheme)),
                    ProfileRow(Icons.Default.Language, appString(state.language, TextKey.AppLanguage)),
                    ProfileRow(Icons.Default.Notifications, appString(state.language, TextKey.Notifications))
                ),
                onClick = { index ->
                    when (index) {
                        0 -> listener.onThemeSelectorRequested()
                        1 -> listener.onLanguageSelectorRequested()
                        else -> listener.onUnavailableActionSelected()
                    }
                }
            )
        }
        item {
            ProfileSection(
                title = appString(state.language, TextKey.Social),
                tag = "social_section",
                rows = listOf(
                    ProfileRow(Icons.Default.Language, "Telegram"),
                    ProfileRow(Icons.Default.Language, "WhatsApp"),
                    ProfileRow(Icons.Default.Language, "Instagram"),
                    ProfileRow(Icons.Default.Language, "Facebook")
                ),
                onClick = { listener.onUnavailableActionSelected() }
            )
            Spacer(Modifier.height(28.dp))
        }
    }

}

@Composable
private fun ProfileHeader(
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
                end = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = appString(language, TextKey.Profile),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onLocationChange,
            modifier = Modifier
                .testTag("profile_change_location")
                .semantics {
                    contentDescription = changeLocationDescription
                }
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null)
        }
        IconButton(
            onClick = onLanguageChange,
            modifier = Modifier
                .testTag("profile_change_language")
                .semantics {
                    contentDescription = changeLanguageDescription
                }
        ) {
            Icon(Icons.Default.Language, contentDescription = null)
        }
    }
}

@Composable
private fun ProfileSummary(
    state: ProfileContract.State,
    onEdit: () -> Unit
) {
    val editProfileDescription = appString(state.language, TextKey.EditProfile)

    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = HealthierTokens.pageHorizontalPadding)
            .testTag("account_card")
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                name = state.profile.name,
                avatarReference = state.profile.avatarReference
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = state.profile.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = appString(state.language, TextKey.Profile),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            IconButton(
                onClick = onEdit,
                modifier = Modifier
                .testTag("edit_profile")
                .semantics {
                        contentDescription = editProfileDescription
                    }
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ProfileAvatar(
    name: String,
    avatarReference: String?
) {
    if (avatarReference == null) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(1).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        AsyncImage(
            model = avatarReference,
            contentDescription = name,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .size(72.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Composable
private fun ProfileSection(
    title: String,
    tag: String,
    rows: List<ProfileRow>,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = HealthierTokens.pageHorizontalPadding)
            .testTag(tag)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            rows.forEachIndexed { index, row ->
                if (index > 0) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(start = 56.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(index) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = row.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = row.label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private data class ProfileRow(
    val icon: ImageVector,
    val label: String
)
