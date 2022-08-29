package com.dnovaes.pokemontcg

import com.dnovaes.pokemontcg.commonFeature.repository.mapper.TcgMapper
import com.dnovaes.pokemontcg.singleCard.domain.model.set.SetResponse
import com.dnovaes.pokemontcg.singleCard.domain.model.set.SetSearchResponse
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

class TcgMapperTest {

    @Test
    fun `check if tcgMapper is filtering SetResponses with tg characters`() {
        val tcgMapper = TcgMapper()
        val setResponse = SetResponse("sw10tg", "name-lowercase", mockk(relaxed = true))
        val setResponseUp = SetResponse("sw10TG", "name-uppercase", mockk(relaxed = true))
        val listResponses = listOf(setResponse, setResponseUp)
        val resultSetResponse = SetSearchResponse(listResponses, 1, 1, 1, 1)
        val mappedResult = tcgMapper.mapSet(resultSetResponse)
        assertEquals(0, mappedResult.size)
    }
}