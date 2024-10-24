package com.example.newsly.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsly.presentation.screen.bookmark.BookmarksScreen
import com.example.newsly.presentation.screen.newsdetail.NewsDetailScreen
import com.example.newsly.presentation.screen.newslist.NewsListScreen

@Composable
fun SetNavigation() {
    val navController = rememberNavController()
    var showBottomNavigation by rememberSaveable {
        mutableStateOf(true)
    }
    val selectedItemIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            if (showBottomNavigation) {
                BottomNavigation(
                    navController = navController,
                    selectedItemIndex = selectedItemIndex)
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController,
            route = "root",
            startDestination = "news"
        ) {
            composable("news") {
                showBottomNavigation = true
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    NewsListScreen(navController)
                }
            }
            composable("bookmarks") {
                showBottomNavigation = true
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    BookmarksScreen(navController)
                }
            }
            composable(
                route = "news_detailed/{title}?category={category}",
                arguments = listOf(
                    navArgument("title") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("category") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                showBottomNavigation = false
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val category = backStackEntry.arguments?.getString("category") ?: ""
                NewsDetailScreen(title = title, category = category, navController = navController)
            }
        }
    }
}