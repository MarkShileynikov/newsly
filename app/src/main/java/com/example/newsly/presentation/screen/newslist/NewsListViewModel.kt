package com.example.newsly.presentation.screen.newslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.News
import com.example.newsly.domain.usecase.FetchNewsUseCase
import com.example.newsly.presentation.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val fetchNewsUseCase: FetchNewsUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<News>>>(ViewState.Loading)
    val viewState: StateFlow<ViewState<List<News>>> = _viewState

    private val categories = listOf(
        context.getString(R.string.business),
        context.getString(R.string.health),
        context.getString(R.string.sports),
        context.getString(R.string.general),
        context.getString(R.string.entertainment),
        context.getString(R.string.technology)
    )

    fun getCategories() : List<String> = categories

    fun getNews(category: String) {
        viewModelScope.launch {
            fetchNewsUseCase(category)
                .onStart {
                    _viewState.value = ViewState.Loading
                }
                .catch {
                    _viewState.value = ViewState.Failure(it.message ?: context.getString(R.string.no_news))
                }
                .collect { news ->
                    if (news.isEmpty()) {
                        _viewState.value = ViewState.Failure(context.getString(R.string.no_news))
                    } else {
                        _viewState.value = ViewState.Success(news)
                    }
                }
        }
    }
}