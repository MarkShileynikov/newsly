package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(title: String) {
        newsRepository.deleteBookmark(title)
    }
}