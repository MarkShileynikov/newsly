package com.example.newsly.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsly.data.database.entity.BookmarkDetailedItem
import com.example.newsly.data.database.entity.BookmarkItem

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmarkDetailedItem: BookmarkDetailedItem)

    @Query("SELECT id, title, description, source FROM bookmarks")
    suspend fun fetchAllBookmarks(): List<BookmarkItem>

    @Query("SELECT * FROM bookmarks WHERE title = :title")
    suspend fun fetchBookmarkByTitle(title: String): BookmarkDetailedItem

    @Query("SELECT COUNT(*) FROM bookmarks WHERE title = :title")
    suspend fun isBookmarkExists(title: String): Int

    @Query("DELETE FROM bookmarks WHERE title = :title")
    suspend fun deleteBookmark(title: String)
}