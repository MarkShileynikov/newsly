package com.example.newsly.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsly.R
import com.example.newsly.domain.entity.News

@Composable
fun NewsCard(
    news: News,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = news.title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = news.description,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${stringResource(id = R.string.source)}: ${news.source}",
            color = Color.Gray
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}