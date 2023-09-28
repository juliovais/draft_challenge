package com.example.bookstore.core.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "volume")
data class VolumeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "authors")
    val authors: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String? = null,
    @ColumnInfo(name = "buy_link")
    var buyLink: String? = null,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
