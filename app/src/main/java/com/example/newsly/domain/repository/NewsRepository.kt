package com.example.newsly.domain.repository

import com.example.newsly.domain.entity.Bookmark
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.util.Event
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun fetchNews(category: String): Event<List<News>>

    suspend fun fetchFullNews(category: String, title: String): Event<List<NewsDetails>>

    suspend fun addBookmark(news: NewsDetails)

    suspend fun fetchAllBookmarks() : Flow<List<Bookmark>>

    suspend fun isBookmarkExists(title: String) : Flow<Boolean>

    suspend fun deleteBookmark(title: String)

}