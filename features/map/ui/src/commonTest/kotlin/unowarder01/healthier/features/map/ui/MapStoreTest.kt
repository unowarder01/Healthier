package unowarder01.healthier.features.map.ui

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import unowarder01.healthier.features.city.domain.Clinic

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MapStoreTest {
    @Test
    fun markerSelectionAndDismissalDriveSheetState() = runTest {
        val clinic = Clinic("c1", "tbilisi", "Clinic", "Care", "Address", 1.0, 2.0, null)
        val store = MapViewModel(listOf(clinic)).store
        var latest = MapContract.State(listOf(clinic))
        store.start(backgroundScope)
        with(store) { backgroundScope.subscribe { states.collect { latest = it } } }
        runCurrent()

        store.intent(MapContract.Intent.SelectClinic("c1"))
        runCurrent()
        assertEquals(clinic, latest.selectedClinic)

        store.intent(MapContract.Intent.DismissClinic)
        runCurrent()
        assertNull(latest.selectedClinic)
    }
}
