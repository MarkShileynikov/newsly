package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.News
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(category: String): Flow<List<News>> = flow{

        val event = newsRepository.fetchNews(category)

        when(event) {
            is Event.Success -> {
                val news = event.data
                emit(
                    news.filter {
                        it.title != "[Removed]"
                    }
                )
            }
            is Event.Failure -> {
                throw Exception(event.exception)
            }
        }
    }
}