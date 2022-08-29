package com.dnovaes.pokemontcg.singleCard.domain.model.set

import com.dnovaes.commons.data.model.ServerResponseInterface
import kotlinx.serialization.Serializable

interface SetSearchResponseInterface: ServerResponseInterface {
    val data: List<SetResponse>
    val page: Int
    val pageSize: Int
    val count: Int
    val totalCount: Int
}

@Serializable
data class SetSearchResponse(
    override val data: List<SetResponse>,
    override val page: Int,
    override val pageSize: Int,
    override val count: Int,
    override val totalCount: Int
): SetSearchResponseInterface
