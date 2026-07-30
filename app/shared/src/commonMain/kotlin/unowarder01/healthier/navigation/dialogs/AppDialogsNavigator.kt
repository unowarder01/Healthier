package unowarder01.healthier.navigation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.launch
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.common.AppTheme
import unowarder01.healthier.core.designsystem.HealthierTokens
import unowarder01.healthier.core.designsystem.TextKey
import unowarder01.healthier.core.designsystem.appString
import unowarder01.healthier.core.platform.PhotoPicker
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.ui.ProfileContract.Message
import unowarder01.healthier.features.profile.ui.ProfileContract.Message.ComingSoon

interface AppDialogsNavigator {
    val router: Value<ChildSlot<AppDialogConfig, AppDialogChild>>

    fun showProfileEditor(
        profile: Profile,
        language: AppLanguage,
        onSave: (name: String, avatarReference: String?) -> Unit
    )

    fun showLanguageSelector(
        language: AppLanguage,
        onSelect: (AppLanguage) -> Unit
    )

    fun showThemeSelector(
        language: AppLanguage,
        theme: AppTheme,
        onSelect: (AppTheme) -> Unit
    )

    fun showMessage(
        language: AppLanguage,
        message: Message
    )

    fun dismiss()

    @Composable
    fun getContentByChild(child: AppDialogChild)
}

class AppDialogsNavigatorImpl(
    context: ComponentContext,
    private val photoPicker: PhotoPicker
) : AppDialogsNavigator,
    ComponentContext by context {
    private val navigation = SlotNavigation<AppDialogConfig>()
    private var profileOutput: ((String, String?) -> Unit)? = null
    private var languageOutput: ((AppLanguage) -> Unit)? = null
    private var themeOutput: ((AppTheme) -> Unit)? = null

    override val router = childSlot(
        key = "AppDialogsNavigator",
        source = navigation,
        serializer = null,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun showProfileEditor(
        profile: Profile,
        language: AppLanguage,
        onSave: (name: String, avatarReference: String?) -> Unit
    ) {
        clearOutputs()
        profileOutput = onSave
        navigation.activate(AppDialogConfig.ProfileEditor(profile, language))
    }

    override fun showLanguageSelector(
        language: AppLanguage,
        onSelect: (AppLanguage) -> Unit
    ) {
        clearOutputs()
        languageOutput = onSelect
        navigation.activate(AppDialogConfig.LanguageSelector(language))
    }

    override fun showThemeSelector(
        language: AppLanguage,
        theme: AppTheme,
        onSelect: (AppTheme) -> Unit
    ) {
        clearOutputs()
        themeOutput = onSelect
        navigation.activate(AppDialogConfig.ThemeSelector(language, theme))
    }

    override fun showMessage(
        language: AppLanguage,
        message: Message
    ) {
        clearOutputs()
        navigation.activate(AppDialogConfig.MessageDialog(language, message))
    }

    override fun dismiss() {
        navigation.dismiss()
        clearOutputs()
    }

    private fun createChild(
        config: AppDialogConfig,
        context: ComponentContext
    ): AppDialogChild = when (config) {
        is AppDialogConfig.ProfileEditor ->
            AppDialogChild.ProfileEditor(config)
        is AppDialogConfig.LanguageSelector ->
            AppDialogChild.LanguageSelector(config)
        is AppDialogConfig.ThemeSelector ->
            AppDialogChild.ThemeSelector(config)
        is AppDialogConfig.MessageDialog ->
            AppDialogChild.MessageDialog(config)
    }

    @Composable
    override fun getContentByChild(child: AppDialogChild) {
        when (child) {
            is AppDialogChild.ProfileEditor -> ProfileEditor(child.config)
            is AppDialogChild.LanguageSelector -> LanguageSelector(child.config)
            is AppDialogChild.ThemeSelector -> ThemeSelector(child.config)
            is AppDialogChild.MessageDialog -> MessageDialog(child.config)
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun ProfileEditor(config: AppDialogConfig.ProfileEditor) {
        var name by remember(config.profile) { mutableStateOf(config.profile.name) }
        var avatarReference by remember(config.profile) {
            mutableStateOf(config.profile.avatarReference)
        }
        var pickerUnavailable by remember(config.profile) { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            onDismissRequest = ::dismiss,
            modifier = Modifier.testTag("profile_edit_sheet")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = HealthierTokens.pageHorizontalPadding,
                        end = HealthierTokens.pageHorizontalPadding,
                        bottom = 24.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = appString(config.language, TextKey.EditProfile),
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(appString(config.language, TextKey.Name)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_name")
                )
                TextButton(
                    onClick = {
                        scope.launch {
                            when (val result = photoPicker.pickAvatar()) {
                                is AppResult.Success -> {
                                    avatarReference = result.value
                                    pickerUnavailable = false
                                }
                                is AppResult.Failure -> pickerUnavailable = true
                            }
                        }
                    },
                    modifier = Modifier.testTag("profile_pick_avatar")
                ) {
                    Text(appString(config.language, TextKey.Avatar))
                }
                if (pickerUnavailable) {
                    Text(
                        text = appString(config.language, TextKey.NotConfigured),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        val output = profileOutput
                        profileOutput = null
                        navigation.dismiss()
                        output?.invoke(name, avatarReference)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_save")
                ) {
                    Text(appString(config.language, TextKey.Save))
                }
            }
        }
    }

    @Composable
    private fun LanguageSelector(config: AppDialogConfig.LanguageSelector) {
        ChoiceDialog(
            title = appString(config.language, TextKey.AppLanguage),
            values = AppLanguage.entries.map(AppLanguage::nativeName),
            onSelect = { index ->
                val output = languageOutput
                languageOutput = null
                navigation.dismiss()
                output?.invoke(AppLanguage.entries[index])
            }
        )
    }

    @Composable
    private fun ThemeSelector(config: AppDialogConfig.ThemeSelector) {
        ChoiceDialog(
            title = appString(config.language, TextKey.ColorTheme),
            values = listOf(
                appString(config.language, TextKey.ThemeSystem),
                appString(config.language, TextKey.ThemeLight),
                appString(config.language, TextKey.ThemeDark)
            ),
            onSelect = { index ->
                val output = themeOutput
                themeOutput = null
                navigation.dismiss()
                output?.invoke(AppTheme.entries[index])
            }
        )
    }

    @Composable
    private fun MessageDialog(config: AppDialogConfig.MessageDialog) {
        AlertDialog(
            onDismissRequest = ::dismiss,
            confirmButton = {
                TextButton(onClick = ::dismiss) {
                    Text(appString(config.language, TextKey.Close))
                }
            },
            text = {
                Text(
                    appString(
                        language = config.language,
                        key = if (config.message == ComingSoon) {
                            TextKey.ComingSoon
                        } else {
                            TextKey.NotConfigured
                        }
                    )
                )
            }
        )
    }

    @Composable
    private fun ChoiceDialog(
        title: String,
        values: List<String>,
        onSelect: (Int) -> Unit
    ) {
        AlertDialog(
            onDismissRequest = ::dismiss,
            confirmButton = {},
            title = { Text(title) },
            text = {
                Column {
                    values.forEachIndexed { index, value ->
                        TextButton(
                            onClick = { onSelect(index) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(value)
                        }
                    }
                }
            }
        )
    }

    private fun clearOutputs() {
        profileOutput = null
        languageOutput = null
        themeOutput = null
    }
}

sealed interface AppDialogConfig {
    data class ProfileEditor(
        val profile: Profile,
        val language: AppLanguage
    ) : AppDialogConfig

    data class LanguageSelector(
        val language: AppLanguage
    ) : AppDialogConfig

    data class ThemeSelector(
        val language: AppLanguage,
        val selected: AppTheme
    ) : AppDialogConfig

    data class MessageDialog(
        val language: AppLanguage,
        val message: Message
    ) : AppDialogConfig
}

sealed interface AppDialogChild {
    data class ProfileEditor(
        val config: AppDialogConfig.ProfileEditor
    ) : AppDialogChild

    data class LanguageSelector(
        val config: AppDialogConfig.LanguageSelector
    ) : AppDialogChild

    data class ThemeSelector(
        val config: AppDialogConfig.ThemeSelector
    ) : AppDialogChild

    data class MessageDialog(
        val config: AppDialogConfig.MessageDialog
    ) : AppDialogChild
}
