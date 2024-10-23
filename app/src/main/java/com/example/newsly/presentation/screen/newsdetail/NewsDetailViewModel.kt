package com.example.newsly.presentation.screen.newsdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.NewsDetails
import com.example.newsly.domain.usecase.AddBookmarkUseCase
import com.example.newsly.domain.usecase.BookmarkCheckUseCase
import com.example.newsly.domain.usecase.DeleteBookmarkUseCase
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
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<NewsDetails>>(ViewState.Loading)
    val viewState : StateFlow<ViewState<NewsDetails>> = _viewState

    private val _bookmarkState = MutableStateFlow(false)
    val bookmarkState : StateFlow<Boolean> = _bookmarkState

    fun getNewsDetails(category: String, title: String) {
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

    fun addBookMark(news: NewsDetails) {
        _bookmarkState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            addBookmarkUseCase(news)
        }
    }

    fun checkIfBookmark(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkCheckUseCase(title)
                .catch {
                    _bookmarkState.value = false
                }
                .collect { isBookmark ->
                    _bookmarkState.value = isBookmark
                }
        }
    }

    fun deleteBookmark(title: String) {
        _bookmarkState.value = false
        viewModelScope.launch {
            deleteBookmarkUseCase(title)
        }
    }
}