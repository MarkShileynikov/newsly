package com.example.newsly.data.response

import android.content.Context
import com.example.newsly.R
import com.example.newsly.domain.entity.NewsDetails
import com.google.gson.annotations.SerializedName

data class FullNewsResponse(
    val articles: List<FullArticle>
)

data class FullArticle(
    val source: Source,
    val author: String?,
    val title: String?,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("publishedAt")
    val publicationDate: String?,
    val content: String?,
    val url: String?,
    val description: String?
)

fun FullNewsResponse.toNewsDetails(context: Context): List<NewsDetails> {
    return articles.map { article -> {}
        NewsDetails(
            source = article.source.name ?: context.getString(R.string.no_source),
            author = article.author ?: context.getString(R.string.no_author),
            title = article.title ?: context.getString(R.string.no_title),
            imageUrl = article.imageUrl ?: "",
            publicationDate = article.publicationDate ?: "",
            content = article.content ?: "",
            url = article.url ?: "",
            description = article.description ?: ""
        )
    }

}
