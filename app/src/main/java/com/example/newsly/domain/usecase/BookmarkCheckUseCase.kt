package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkCheckUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){
    suspend operator fun invoke(title: String) : Flow<Boolean> {
        return newsRepository.isBookmarkExists(title)
    }
}