package unowarder01.healthier

import androidx.compose.ui.test.hasTestTag
import com.atiurin.ultron.core.compose.activity.createAndroidComposeRule
import com.atiurin.ultron.extensions.assertDoesNotExist
import com.atiurin.ultron.extensions.assertIsDisplayed
import com.atiurin.ultron.extensions.clearText
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.inputText
import org.junit.Rule
import org.junit.Test

class HealthierUltronSmokeTest {
    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun splashAuthCityHomeAndMapScenario() {
        hasTestTag("language_container").assertIsDisplayed()
        hasTestTag("language_en").click()

        hasTestTag("auth_google").assertIsDisplayed()
        hasTestTag("auth_apple").assertDoesNotExist()
        hasTestTag("auth_google").click()
        hasTestTag("city_list").assertIsDisplayed()

        hasTestTag("city_search").inputText("no-such-city")
        hasTestTag("city_empty").assertIsDisplayed()
        hasTestTag("city_search").clearText()
        hasTestTag("city_list").assertIsDisplayed()
        hasTestTag("city_tbilisi").click()

        hasTestTag("home_bottom_navigation").assertIsDisplayed()
        hasTestTag("tab_profile").click()
        hasTestTag("profile_screen").assertIsDisplayed()
        hasTestTag("account_card").assertIsDisplayed()

        hasTestTag("tab_map").click()
        hasTestTag("map_screen").assertIsDisplayed()
        hasTestTag("map_marker_tbilisi-central").click()
        hasTestTag("clinic_bottom_sheet").assertIsDisplayed()
    }
}
