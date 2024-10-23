package com.example.newsly.data.response

import android.content.Context
import com.example.newsly.R
import com.example.newsly.domain.entity.News

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String?,
    val description: String?,
    val source: Source
)

data class Source(
    val name: String?
)

fun NewsResponse.toNewsList(context: Context): List<News> {
    return articles.map {article ->
        News(
            title = article.title ?: context.getString(R.string.no_title),
            description = article.description ?: context.getString(R.string.no_description),
            source = article.source.name ?: context.getString(R.string.no_source),
        )
    }
}
