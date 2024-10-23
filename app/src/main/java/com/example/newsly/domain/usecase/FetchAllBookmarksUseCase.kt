package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.Bookmark
import com.example.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllBookmarksUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() : Flow<List<Bookmark>> = newsRepository.fetchAllBookmarks()
}