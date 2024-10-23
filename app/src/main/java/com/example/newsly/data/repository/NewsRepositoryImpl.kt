package com.example.newsly.data.repository

import android.content.Context
import com.example.newsly.R
import com.example.newsly.data.api.NetworkClientConfig
import com.example.newsly.data.api.NewsApiService
import com.example.newsly.data.api.util.doCall
import com.example.newsly.data.response.toFullNews
import com.example.newsly.data.response.toNewsList
import com.example.newsly.data.util.isConnectedToNetwork
import com.example.newsly.domain.entity.FullNews
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.util.Event
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    @ApplicationContext private val context: Context
): NewsRepository {

    override suspend fun fetchNews(category: String): Event<List<News>> {
        if (!isConnectedToNetwork(context)) {
            return Event.Failure(context.getString(R.string.no_internet))
        }

        val event = doCall {
            return@doCall newsApiService.fetchNewsList(NetworkClientConfig.API_KEY, category)
        }

        return when(event) {
            is Event.Success -> {
                val response = event.data
                val news = response.toNewsList(context)
                Event.Success(news)
            }
            is Event.Failure -> {
                val error = event.exception
                Event.Failure(error)
            }
        }
    }

    override suspend fun fetchFullNews(category: String, title: String): Event<List<FullNews>> {
        if (!isConnectedToNetwork(context)) {
            return Event.Failure(context.getString(R.string.no_internet))
        }

        val event = doCall {
            return@doCall newsApiService.fetchNews(NetworkClientConfig.API_KEY, category, title)
        }

        return when(event) {
            is Event.Success -> {
                val response = event.data
                val article = response.toFullNews(context)
                Event.Success(article)
            }
            is Event.Failure -> {
                val error = event.exception
                Event.Failure(error)
            }
        }
    }
}