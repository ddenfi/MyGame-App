package com.ddenfi.expertcapstone.core.domain.model

data class GameDetail(
    val id: Int,
    val name: String?,
    val backgroundImage: String?,
    val description: String?,
    val playTime: Int?,
    val esrbRating: String? ,
    val rating: String?,
    val parentPlatforms: List<Int>?,
    val released: String?,
    var isFavorite: Boolean? = false
)
