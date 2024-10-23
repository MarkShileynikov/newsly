package com.example.newsly.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val screen: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

fun provideItems() : List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            screen = "home",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomNavigationItem(
            screen = "bookmarks",
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            selectedIcon = Icons.Filled.Favorite
        ),
    )
}
