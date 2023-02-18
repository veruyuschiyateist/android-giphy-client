package com.gph.tst.giphytestapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifs")
data class GiphyLocalEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val height: Int,
    val width: Int,
    val removed: Boolean = false,
)
