package com.example.newsly.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavController

@Composable
fun BottomNavigation(
    navController: NavController,
    selectedItemIndex: MutableIntState
) {

    val newsItem = BottomNavigationItem.provideNewsItem()
    val bookmarkItem = BottomNavigationItem.provideBookmarkItem()

    NavigationBar {
        NavigationBarItem(
            selected = newsItem.index == selectedItemIndex.intValue,
            onClick = {
                if (selectedItemIndex.intValue != newsItem.index) {
                    selectedItemIndex.intValue = newsItem.index
                    navController.navigate(newsItem.screen) {
                        popUpTo(bookmarkItem.screen) { inclusive = true }
                    }
                }
            },
            icon = {
                Icon(imageVector = if (newsItem.index == selectedItemIndex.intValue) {
                    newsItem.selectedIcon
                } else {
                    newsItem.unselectedIcon
                },
                    contentDescription = newsItem.screen
                )
            }
        )
        NavigationBarItem(
            selected = bookmarkItem.index == selectedItemIndex.intValue,
            onClick = {
                if (selectedItemIndex.intValue != bookmarkItem.index) {
                    selectedItemIndex.intValue = bookmarkItem.index
                    navController.navigate(bookmarkItem.screen) {
                        popUpTo(newsItem.screen) { inclusive = true }
                    }
                }
            },
            icon = {
                Icon(imageVector = if (selectedItemIndex.intValue == bookmarkItem.index) {
                    bookmarkItem.selectedIcon
                } else {
                    bookmarkItem.unselectedIcon
                },
                    contentDescription = bookmarkItem.screen
                )
            }
        )
    }
}