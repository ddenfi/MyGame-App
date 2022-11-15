package com.ddenfi.expertcapstone.core.utils

import com.ddenfi.expertcapstone.core.data.source.local.entity.GameDetailEntity
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

    fun mapGameDetailResponseToGameDetailEntity(remote: GameDetailResponse): GameDetailEntity =
        GameDetailEntity(
            id = remote.id,
            name = remote.name,
            backgroundImage = remote.backgroundImage,
            description = remote.description,
            playtime = remote.playtime,
            esrbRating = remote.esrbRating.name,
            rating = remote.rating.toString(),
            parentPlatforms = remote.parentPlatforms.map { it.platform.id },
            released = remote.released,
            isFavorite = false

        )

    fun mapGameDetailEntityToGameDetail(local: GameDetailEntity):GameDetail =
        GameDetail(
            id = local.id,
            name = local.name,
            backgroundImage = local.backgroundImage,
            description = local.description,
            playtime = local.playtime,
            esrbRating = local.esrbRating,
            rating = local.rating.toString(),
            parentPlatforms = local.parentPlatforms,
            released = local.released,
            isFavorite = false
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