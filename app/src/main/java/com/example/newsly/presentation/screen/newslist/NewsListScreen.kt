package com.example.newsly.presentation.screen.newslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsly.R
import com.example.newsly.domain.entity.News
import com.example.newsly.presentation.component.NewsCard

@Composable
fun NewsListScreen() {

    val viewModel: NewsListViewModel = viewModel()
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val categories = viewModel.getCategories()

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
            modifier = Modifier.padding(16.dp)
        )

        ScrollableTabRow(selectedTabIndex = selectedTabIndex ) {
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
        val news = listOf(
            News(
                title = "Title",
                description = "Description",
                source = "Source"
            )
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(news) {newsItem ->
                NewsCard(news = newsItem)
            }
        }
    }
}