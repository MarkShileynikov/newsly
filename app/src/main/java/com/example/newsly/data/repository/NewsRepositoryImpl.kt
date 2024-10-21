package com.example.newsly.data.repository

import android.content.Context
import com.example.newsly.R
import com.example.newsly.data.api.NetworkClientConfig
import com.example.newsly.data.api.NewsApiService
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    @ApplicationContext private val context: Context
): NewsRepository {
    override suspend fun fetchNews(category: String): Flow<List<News>> = flow {
        val response = newsApiService.fetchNews(NetworkClientConfig.API_KEY, category)
        emit(response)
    }.map { responses ->
        responses.articles.map { response ->
            News(
                title = response.title ?: context.getString(R.string.no_title),
                description = response.description ?: context.getString(R.string.no_description),
                source = response.source.name ?: context.getString(R.string.no_source)
            )
        }
    }
}