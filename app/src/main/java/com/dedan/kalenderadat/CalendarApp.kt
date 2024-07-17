package com.dedan.kalenderadat

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dedan.kalenderadat.model.navigationItemList
import com.dedan.kalenderadat.ui.navigation.CalendarNavHost
import kotlinx.coroutines.launch

@Composable
fun CalendarApp(navController: NavHostController = rememberNavController()) {
    CalendarNavHost(navController)
}

@Composable
fun CalendarDrawer() {
    ModalDrawerSheet {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
//        navigationIcon = {
//            IconButton(
//                onClick = {
//                    scope.launch {
//                        drawerState.open()
//                    }
//                }
//            ) {
//                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
//            }
//        },
        title = {
            Text(text = "Kalender Bali")
        }
    )
}