package com.example.newsly.presentation.screen.newslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.newsly.R

class NewsListViewModel(context: Application): AndroidViewModel(context) {

    private val categories = listOf(
        context.getString(R.string.business),
        context.getString(R.string.health),
        context.getString(R.string.sports),
        context.getString(R.string.general),
        context.getString(R.string.entertainment),
        context.getString(R.string.technology)
    )

    fun getCategories() : List<String> = categories
}