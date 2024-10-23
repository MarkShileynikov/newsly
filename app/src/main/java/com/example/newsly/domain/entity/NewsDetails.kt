package com.example.newsly.domain.entity

data class NewsDetails(
    val source: String,
    val author: String,
    val title: String,
    val imageUrl: String,
    val publicationDate: String,
    val content: String,
    val url: String,
    val description: String
)
