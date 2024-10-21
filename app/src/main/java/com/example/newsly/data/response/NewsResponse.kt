package com.example.newsly.data.response

data class NewsApiResponse(
    val articles: List<NewsResponse>
)

data class NewsResponse(
    val title: String?,
    val description: String?,
    val source: Source
)

data class Source(
    val name: String?
)
