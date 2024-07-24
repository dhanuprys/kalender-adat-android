package com.dedan.kalenderadat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
fun CalendarAppBar(
    navigateToNoteList: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Kalender Bali")
                IconButton(onClick = navigateToNoteList) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_note),
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun CalendarAppBarPreview() {
    Scaffold(
        topBar = { CalendarAppBar(navigateToNoteList = {}) },
    ) { innerPadding ->
        innerPadding
    }
}