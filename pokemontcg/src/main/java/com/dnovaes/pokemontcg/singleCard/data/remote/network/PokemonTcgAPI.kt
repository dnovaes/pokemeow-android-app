package com.dnovaes.pokemontcg.singleCard.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
data class ServerResponse(
    val data: TcgDataResponse,
)

@Serializable
data class TcgDataResponse(
    val id: String,
    val name: String,
    val hp: String
)

interface PokemonTcgAPI {

    /*
    https://docs.pokemontcg.io/api-reference/cards/get-card
    GET https://api.pokemontcg.io/v2/cards/<id>
    */
    @GET("/v2/cards/{id}")
    suspend fun getCard(@Path("id") id: String): ServerResponse

}