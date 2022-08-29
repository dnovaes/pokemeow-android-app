package com.dnovaes.pokemontcg.singleCard.data.remote.network

import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponse
import com.dnovaes.pokemontcg.singleCard.domain.model.set.SetSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val TCG_SERVICE_URL = "https://api.pokemontcg.io/"

interface PokemonTcgAPIInterface {

    /*
    https://docs.pokemontcg.io/api-reference/cards/get-card
    */
    @GET("/v2/cards/{id}")
    suspend fun getCard(@Path("id") id: String): CardResponse

    /*
    https://docs.pokemontcg.io/api-reference/sets/search-sets/
     */
    @GET("/v2/sets?")
    suspend fun getAllSets(@Query("select") select: String): SetSearchResponse


}