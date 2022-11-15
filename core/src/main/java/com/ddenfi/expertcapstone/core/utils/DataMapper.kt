package com.ddenfi.expertcapstone.core.utils

import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.remote.response.GameDetailResponse
import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResultsItem
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail

object DataMapper {
    fun mapResponseToEntity(input: GamesResultsItem,page:Int): GameEntity =
        GameEntity(
            id = input.id,
            name = input.name,
            released = input.released,
            rating = input.rating.toString(),
            parentPlatform = input.parentPlatforms.map { it.platform.id },
            isFavorite = false,
            page = page
        )


    fun mapRemoteAndLocalToGameDetail(remote: GameDetailResponse?, local: GameEntity): GameDetail =
        GameDetail(
            id = local.id,
            name = local.name,
            backgroundImage = remote?.backgroundImage ?: "",
            description = remote?.description ?: "",
            playtime = remote?.playtime ?: 0,
            esrbRating = remote?.esrbRating?.name ?: "",
            rating = local.rating,
            parentPlatforms = local.parentPlatform,
            released = local.released,
            isFavorite = local.isFavorite

        )

    fun mapGameEntityToGame(input: GameEntity): Game =
        Game(
            id = input.id,
            name = input.name ?: "",
            released = input.released ?: "",
            rating = input.rating ?: "",
            parentPlatforms = input.parentPlatform ?: listOf(),
            isFavorite = false
        )

}