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
    val desc:String?= ""
)
