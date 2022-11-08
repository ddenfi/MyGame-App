package com.ddenfi.expertcapstone.core.domain.model

data class GameInformation(
    val id: Int,
    val name: String,
    val rating: String,
    val parentPlatforms: List<Int>,
    val released: String,
    val isFavorite: Boolean? = false,
    val backgroundImage: String,
    val description: String,
    val playtime: Int,
    val esrbRating: String
)