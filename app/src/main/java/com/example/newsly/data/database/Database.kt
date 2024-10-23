package com.example.newsly.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsly.data.database.dao.BookmarkDao
import com.example.newsly.data.database.entity.BookmarkDetailedItem

@Database(entities = [BookmarkDetailedItem::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao
}