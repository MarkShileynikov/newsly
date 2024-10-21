package com.example.newsly.domain

import com.example.newsly.domain.entity.News
import com.example.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String): Flow<List<News>> = newsRepository.fetchNews(category)
}