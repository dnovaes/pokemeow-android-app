package com.dnovaes.pokemontcg.singleCard.data.remote.network

import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val TCG_SERVICE_URL = "https://api.pokemontcg.io/"

interface PokemonTcgAPIInterface {

    /*
    https://docs.pokemontcg.io/api-reference/cards/get-card
    GET https://api.pokemontcg.io/v2/cards/<id>
    */
    @GET("/v2/cards/{id}")
    suspend fun getCard(@Path("id") id: String): CardResponse

}