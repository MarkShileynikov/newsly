package com.example.newsly.presentation.screen.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsly.R
import com.example.newsly.domain.entity.Bookmark
import com.example.newsly.domain.entity.News
import com.example.newsly.presentation.component.NewsCard
import com.example.newsly.presentation.util.ViewState

@Composable
fun BookmarksScreen(
    navController: NavController
) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    when(viewState) {
        is ViewState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp),
                    color = Color.Red
                )
            }
        }
        is ViewState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (viewState as ViewState.Failure).message,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        is ViewState.Success -> {
            val bookMarks = (viewState as ViewState.Success<List<Bookmark>>).data

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.bookmarks),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn {
                    items(bookMarks) {bookmark ->
                        NewsCard(
                            news = News(
                                title = bookmark.title,
                                description = bookmark.description,
                                source = bookmark.source
                            )
                        ) {

                        }
                    }
                }
            }
        }
    }
}