package com.example.newsly.presentation.screen.bookmark

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.Bookmark
import com.example.newsly.domain.usecase.FetchAllBookmarksUseCase
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
class BookmarkViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchAllBookmarksUseCase: FetchAllBookmarksUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<Bookmark>>>(ViewState.Loading)
    val viewState: StateFlow<ViewState<List<Bookmark>>> = _viewState

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllBookmarksUseCase()
                .onStart {
                    _viewState.value = ViewState.Loading
                }
                .catch {
                    _viewState.value = ViewState.Failure(context.getString(R.string.bookmark_error))
                }
                .collect {bookmarks ->
                    if (bookmarks.isEmpty()) {
                        _viewState.value = ViewState.Failure(context.getString(R.string.no_bookmarks))
                    } else {
                        _viewState.value = ViewState.Success(bookmarks)
                    }
                }
        }
    }
}