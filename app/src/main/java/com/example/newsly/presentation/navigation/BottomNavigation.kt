package com.example.newsly.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigation(
    navController: NavController,
    selectedItemIndex: MutableIntState
) {
    val items = provideItems()
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex.intValue == index,
                onClick = {
                    selectedItemIndex.intValue = index
                    navController.navigate(item.screen)
                },
                icon = {
                    Icon(imageVector = if (index == selectedItemIndex.intValue) {
                        item.selectedIcon
                    } else {
                        item.unselectedIcon
                    },
                        contentDescription = item.screen
                    )
                }
            )
        }
    }
}