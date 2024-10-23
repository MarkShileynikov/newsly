package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.repository.NewsRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(news: NewsDetails) {
        newsRepository.addBookmark(news)
    }
}