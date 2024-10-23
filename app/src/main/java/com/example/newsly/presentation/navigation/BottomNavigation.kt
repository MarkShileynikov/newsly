package com.example.newsly.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigation(
    navController: NavController
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val items = provideItems()
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.screen)
                },
                icon = {
                    Icon(imageVector = if (index == selectedItemIndex) {
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