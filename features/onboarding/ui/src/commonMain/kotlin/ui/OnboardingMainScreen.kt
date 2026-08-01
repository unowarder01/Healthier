package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.OnboardingContract.Listener
import ui.OnboardingContract.State

@Composable
fun OnboardingMainScreen(
    state: State,
    listener: Listener
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    )
}