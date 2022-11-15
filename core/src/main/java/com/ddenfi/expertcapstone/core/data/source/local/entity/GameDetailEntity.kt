package com.ddenfi.expertcapstone.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_detail")
data class GameDetailEntity(
    @PrimaryKey
    val id: Int,
    val name: String?= "",
    val esrbRating:String?= "",
    val playTime:Int?= 0,
    val backgroundImage:String?= "",
    val description:String?= ""  ,
    val playtime:Int?= 0,
    val rating:String?= "" ,
    val parentPlatforms:List<Int>? = listOf(),
    val released:String?="",
    val isFavorite:Boolean = false
)
