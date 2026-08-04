package unowarder01.healthier.features.map.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MapMainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    )
}
