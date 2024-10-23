package com.example.newsly.domain.repository

import com.example.newsly.domain.entity.FullNews
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.util.Event

interface NewsRepository {
    suspend fun fetchNews(category: String): Event<List<News>>

    suspend fun fetchFullNews(category: String, title: String): Event<List<FullNews>>
}