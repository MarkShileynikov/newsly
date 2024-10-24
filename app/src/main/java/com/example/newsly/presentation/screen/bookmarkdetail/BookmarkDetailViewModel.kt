package com.example.newsly.presentation.screen.bookmarkdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.usecase.AddBookmarkUseCase
import com.example.newsly.domain.usecase.DeleteBookmarkUseCase
import com.example.newsly.domain.usecase.FetchBookmarkUseCase
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
class BookmarkDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchBookmarkUseCase: FetchBookmarkUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<NewsDetails>>(ViewState.Loading)
    val viewState : StateFlow<ViewState<NewsDetails>> = _viewState

    private val _isBookmark = MutableStateFlow(true)
    val isBookmark : StateFlow<Boolean> = _isBookmark

    fun getBookmark(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchBookmarkUseCase(id)
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
        if (isBookmark.value) {
            deleteBookmark(news.title)
            _isBookmark.value = false
        }
        else {
            addBookmark(news)
            _isBookmark.value = true
        }
    }

    private fun addBookmark(news: NewsDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            addBookmarkUseCase(news)
        }
    }

    private fun deleteBookmark(title: String) {
        viewModelScope.launch {
            deleteBookmarkUseCase(title)
        }
    }
}