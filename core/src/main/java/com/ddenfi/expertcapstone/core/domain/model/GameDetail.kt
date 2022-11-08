package com.ddenfi.expertcapstone.core.domain.model

data class GameDetail(
    val id: Int,
    val name: String? = "",
    val backgroundImage: String? = "",
    val description: String? = "",
    val playtime: Int? = 0,
    val esrbRating: String? = "",
    val rating: String? = "",
    val parentPlatforms: List<Int>? = listOf(),
    val released: String? = "",
    var isFavorite: Boolean? = false
)
