package com.example.newsly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.newsly.presentation.screen.newslist.NewsListScreen
import com.example.newsly.ui.theme.NewslyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewslyTheme {
                NewsListScreen()
            }
        }
    }
}


