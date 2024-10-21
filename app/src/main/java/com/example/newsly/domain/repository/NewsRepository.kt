package com.example.newsly.domain.repository

import com.example.newsly.domain.entity.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun fetchNews(category: String): Flow<List<News>>
}