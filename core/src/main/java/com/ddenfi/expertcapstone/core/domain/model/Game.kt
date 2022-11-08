package com.ddenfi.expertcapstone.core.domain.model

data class Game(
    val id: Int,
    val name: String,
    val rating: String,
    val parentPlatforms: List<Int>,
    val released: String,
    val isFavorite: Boolean? = false
)
