package com.example.newsly.domain.usecase

import com.example.newsly.domain.entity.FullNews
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchFullNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(category: String, title: String) : Flow<FullNews> = flow {

        val event = newsRepository.fetchFullNews(category, title)

        when(event) {
            is Event.Success -> {
                val article = event.data[0]
                val date = article.publicationDate
                val formattedDate = OffsetDateTime.parse(date).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
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

}