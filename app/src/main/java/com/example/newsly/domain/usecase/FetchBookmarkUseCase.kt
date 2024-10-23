package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(id: Int) : Flow<NewsDetails> = newsRepository.fetchBookmark(id)
}