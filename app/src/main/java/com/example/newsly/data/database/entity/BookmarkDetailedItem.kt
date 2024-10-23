package com.example.newsly.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkDetailedItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: String,
    val author: String,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "publication_date")
    val publicationDate: String,
    val content: String,
    val url: String,
    val description: String
)
