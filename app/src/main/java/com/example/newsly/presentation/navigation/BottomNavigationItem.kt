package com.example.newsly.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

class BottomNavigationItem(
    val index: Int,
    val screen: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    companion object {
        fun provideNewsItem() : BottomNavigationItem {
            return BottomNavigationItem(
                index = 0,
                screen = "news",
                unselectedIcon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home
            )
        }

        fun provideBookmarkItem() : BottomNavigationItem {
            return BottomNavigationItem(
                index = 1,
                screen = "bookmarks",
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selectedIcon = Icons.Filled.Favorite
            )
        }
    }
}


