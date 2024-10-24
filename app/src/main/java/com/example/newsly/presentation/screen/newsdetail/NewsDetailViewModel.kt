package com.example.newsly.presentation.screen.newsdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.usecase.AddBookmarkUseCase
import com.example.newsly.domain.usecase.BookmarkCheckUseCase
import com.example.newsly.domain.usecase.DeleteBookmarkUseCase
import com.example.newsly.domain.usecase.FetchBookmarkUseCase
import com.example.newsly.domain.usecase.FetchFullNewsUseCase
import com.example.newsly.presentation.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val fetchFullNewsUseCase: FetchFullNewsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val bookmarkCheckUseCase: BookmarkCheckUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val fetchBookmarkUseCase: FetchBookmarkUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<NewsDetails>>(ViewState.Loading)
    val viewState : StateFlow<ViewState<NewsDetails>> = _viewState

    private val _isBookmarkState = MutableStateFlow(false)
    val isBookmarkState : StateFlow<Boolean> = _isBookmarkState


    fun getNewsDetails(category: String, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            checkIfBookmark(title)

            if (category.isBlank() || _isBookmarkState.value) {
                getNewsDetailsFromDb(title)
            } else {
                getNewsDetailsFromApi(category, title)
            }
        }
    }

    private fun getNewsDetailsFromApi(category: String, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFullNewsUseCase(category, title)
                .onStart {
                    _viewState.value = ViewState.Loading
                }
                .catch {
                    _viewState.value = ViewState.Failure(it.message ?: context.getString(R.string.error))
                }
                .collect { news ->
                    _viewState.value = ViewState.Success(news)
                }
        }
    }

    private fun getNewsDetailsFromDb(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchBookmarkUseCase(title)
                .onStart { _viewState.value = ViewState.Loading }
                .catch {
                    _viewState.value = ViewState.Failure(context.getString(R.string.error))
                }
                .collect {article ->
                    _viewState.value = ViewState.Success(article)
                }
        }
    }

    fun addOrDeleteBookmark(news: NewsDetails) {
        if (isBookmarkState.value) {
            deleteBookmark(news.title)
            _isBookmarkState.value = false
        } else {
            addBookMark(news)
            _isBookmarkState.value = true
        }
    }

    private fun addBookMark(news: NewsDetails) {
        _isBookmarkState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            addBookmarkUseCase(news)
        }
    }

    private suspend fun checkIfBookmark(title: String) {
        try {
            bookmarkCheckUseCase(title)
                .catch {
                    _isBookmarkState.value = false
                }
                .collect { isBookmark ->
                    _isBookmarkState.value = isBookmark
                }
        } catch (e: Exception) {
            _isBookmarkState.value = false
        }
    }

    private fun deleteBookmark(title: String) {
        _isBookmarkState.value = false
        viewModelScope.launch {
            deleteBookmarkUseCase(title)
        }
    }
}