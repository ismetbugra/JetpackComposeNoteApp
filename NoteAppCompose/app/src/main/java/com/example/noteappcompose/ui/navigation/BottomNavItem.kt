package com.example.noteappcompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    var title:String,
    var selectedIcon:ImageVector,
    var unselectedIcon:ImageVector,
    var route:Destinations
)

object items{
    var list= listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Destinations.Home
        ),
        BottomNavItem(
            title = "Favorites",
            selectedIcon = Icons.Default.Favorite,
            unselectedIcon = Icons.Rounded.Favorite,
            route = Destinations.Favorites
        )
    )
}