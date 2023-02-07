package com.dnovaes.pokemontcg.singleCard.model

import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.data.model.uiviewstate.UIViewState
import com.dnovaes.pokemontcg.singleCard.data.model.SingleCardUIDataProcess
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.Card
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SingleCardUIObservableTest {

    @Test
    fun withResultWorks() {
        val initialUIState = UIViewState<Card>(
            process = SingleCardUIDataProcess.LOADING_SINGLE_CARD,
            state = UIDataState.STARTED,
        )

        assertNull(initialUIState.result)
        assertEquals(SingleCardUIDataProcess.LOADING_SINGLE_CARD, initialUIState.process)
        assertEquals(UIDataState.STARTED, initialUIState.state)

        val expectedCard = Card("10", "Sunkern", "40", mockk(), "")
        val finalUIState = initialUIState.withResult(expectedCard)
        assertEquals(expectedCard, finalUIState.result)
    }

}