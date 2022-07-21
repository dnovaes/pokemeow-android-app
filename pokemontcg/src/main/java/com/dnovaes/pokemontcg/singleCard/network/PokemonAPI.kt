package com.dnovaes.pokemontcg.singleCard.network

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    /*
    https://docs.pokemontcg.io/api-reference/cards/get-card
    GET https://api.pokemontcg.io/v2/cards/<id>
    */
    @GET("/cards/{id}")
    fun getCard(@Path("id") id: String): Result<*>

}