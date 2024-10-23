package com.example.newsly.data.repository

import android.content.Context
import com.example.newsly.R
import com.example.newsly.data.api.NetworkClientConfig
import com.example.newsly.data.api.NewsApiService
import com.example.newsly.data.api.util.doCall
import com.example.newsly.data.database.dao.BookmarkDao
import com.example.newsly.data.database.entity.BookmarkDetailedItem
import com.example.newsly.data.response.toNewsDetails
import com.example.newsly.data.response.toNewsList
import com.example.newsly.data.util.isConnectedToNetwork
import com.example.newsly.domain.entity.Bookmark
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.util.Event
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val bookmarkDao: BookmarkDao,
    @ApplicationContext private val context: Context
): NewsRepository {

    override suspend fun fetchNews(category: String): Event<List<News>> {
        if (!isConnectedToNetwork(context)) {
            return Event.Failure(context.getString(R.string.no_internet))
        }

        val event = doCall {
            return@doCall newsApiService.fetchNewsList(NetworkClientConfig.API_KEY, category)
        }

        return when(event) {
            is Event.Success -> {
                val response = event.data
                val news = response.toNewsList(context)
                Event.Success(news)
            }
            is Event.Failure -> {
                val error = event.exception
                Event.Failure(error)
            }
        }
    }

    override suspend fun fetchFullNews(category: String, title: String): Event<List<NewsDetails>> {
        if (!isConnectedToNetwork(context)) {
            return Event.Failure(context.getString(R.string.no_internet))
        }

        val event = doCall {
            return@doCall newsApiService.fetchNews(NetworkClientConfig.API_KEY, category, title)
        }

        return when(event) {
            is Event.Success -> {
                val response = event.data
                val article = response.toNewsDetails(context)
                Event.Success(article)
            }
            is Event.Failure -> {
                val error = event.exception
                Event.Failure(error)
            }
        }
    }

    override suspend fun addBookmark(news: NewsDetails) {
        bookmarkDao.insert(
            BookmarkDetailedItem(
                source = news.source,
                author = news.author,
                title = news.title,
                imageUrl = news.imageUrl,
                publicationDate = news.publicationDate,
                content = news.content,
                url = news.url,
                description = news.description
            )
        )
    }

    override suspend fun fetchAllBookmarks(): Flow<List<Bookmark>> = flow {
        val bookmarks = bookmarkDao.fetchAllBookmarks()
        emit(bookmarks)
    }.map {bookmarks ->
        bookmarks.map {bookmark ->
            Bookmark(
                id = bookmark.id,
                source = bookmark.source,
                title = bookmark.title,
                description = bookmark.description
            )
        }
    }

    override suspend fun isBookmarkExists(title: String): Flow<Boolean> = flow {
        emit(bookmarkDao.isBookmarkExists(title) > 0)
    }

    override suspend fun deleteBookmark(title: String) {
        bookmarkDao.deleteBookmark(title)
    }
}
