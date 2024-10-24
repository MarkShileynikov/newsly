package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchFullNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(category: String, title: String) : Flow<NewsDetails> = flow {

        val event = newsRepository.fetchFullNews(category, title)

        when(event) {
            is Event.Success -> {
                val article = event.data[0]
                val date = article.publicationDate
                val formattedDate = parseDate(date)
                val content = article.content
                emit(
                    article.copy(
                        publicationDate = formattedDate,
                        content = content.split("[+")[0]
                    )
                )
            }
            is Event.Failure -> {
                throw Exception(event.exception)
            }
        }
    }

    private fun parseDate(date: String): String {
        val datePart = date.split("T")[0]

        val dateComponents = datePart.split("-")
        val year = dateComponents[0]
        val month = dateComponents[1]
        val day = dateComponents[2]

        return "$day.$month.$year"
    }
}