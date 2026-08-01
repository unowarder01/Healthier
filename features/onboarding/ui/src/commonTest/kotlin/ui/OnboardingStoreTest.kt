package ui

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
    }
}
