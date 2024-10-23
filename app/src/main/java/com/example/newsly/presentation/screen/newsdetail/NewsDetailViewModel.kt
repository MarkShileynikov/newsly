package com.example.newsly.presentation.screen.newsdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsly.R
import com.example.newsly.domain.entity.FullNews
import com.example.newsly.domain.usecase.FetchFullNewsUseCase
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
class NewsDetailViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val fetchFullNewsUseCase: FetchFullNewsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<FullNews>>(ViewState.Loading)
    val viewState : StateFlow<ViewState<FullNews>> = _viewState

    fun getNewsDetails(category: String, title: String) {
        viewModelScope.launch {
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
}