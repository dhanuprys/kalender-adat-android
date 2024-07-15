package com.dedan.kalenderadat.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val onClick: (navController: NavHostController) -> Unit = {}
)

val navigationItemList = listOf(
    NavigationItem("Beranda", Icons.Filled.Home) {
        it.navigate(AppRoutes.Home.name)
    },
    NavigationItem("Info", Icons.Filled.Info) {
        it.navigate(AppRoutes.Info.name)
    }
)
