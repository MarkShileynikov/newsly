package com.example.newsly.presentation.screen.newslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.newsly.domain.entity.News
import com.example.newsly.presentation.component.NewsCard
import com.example.newsly.presentation.util.ViewState

@Composable
fun NewsListScreen(
    navController: NavController
) {

    val viewModel: NewsListViewModel = hiltViewModel()
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val categories = viewModel.getCategories()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(selectedTabIndex) {
        viewModel.getNews(categories[selectedTabIndex])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(id = R.string.news),
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = category,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
        when(viewState) {
            is ViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp),
                        color = Color.Red
                    )
                }
            }
            is ViewState.Success -> {
                val newsList = (viewState as ViewState.Success<List<News>>).data

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(newsList) { news ->
                        NewsCard(
                            news = news,
                            onClick = {
                                navController.navigate("news_detailed/${news.title}?category=${categories[selectedTabIndex]}")
                            }
                        )
                    }
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
        }
    }
}