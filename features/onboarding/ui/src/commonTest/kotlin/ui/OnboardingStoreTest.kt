package ui

import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class OnboardingStoreTest {
    @Test
    fun continueAndSwipeSelectTheExpectedOnboardingPage() = runTest {
        val store = OnboardingViewModel().store
        var latest = OnboardingContract.State()
        store.start(backgroundScope)
        with(store) {
            backgroundScope.subscribe { states.collect { latest = it } }
        }
        runCurrent()

        store.intent(OnboardingContract.Intent.OnPositiveButtonClicked)
        runCurrent()
        assertEquals(1, latest.currentPage)

        val actions = mutableListOf<OnboardingContract.Action>()
        with(store) {
            backgroundScope.subscribe { this.actions.collect { actions += it } }
        }
        runCurrent()
        store.intent(OnboardingContract.Intent.OnPositiveButtonClicked)
        store.intent(OnboardingContract.Intent.OnPositiveButtonClicked)
        runCurrent()
        assertTrue(OnboardingContract.Action.NavigateToAuth in actions)
    }
}
