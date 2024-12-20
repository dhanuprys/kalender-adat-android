package com.dedan.kalenderadat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dedan.kalenderadat.model.navigationItemList
import com.dedan.kalenderadat.ui.navigation.CalendarNavHost
import com.dedan.kalenderadat.ui.screen.home.HomeDestination
import com.dedan.kalenderadat.ui.screen.info.InfoDestination
import com.dedan.kalenderadat.ui.screen.notelist.NoteListDestination
import kotlinx.coroutines.launch

@Composable
fun CalendarApp(navController: NavHostController = rememberNavController()) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            CalendarAppDrawer(
                redirectTo = { route, autoClose ->
                    if (autoClose) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }

                    navController.navigate(route)
                }
            )
        }
    ) {
        CalendarNavHost(
            navController = navController,
            onDrawerOpenRequest = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAppBar(
    onDrawerOpenRequest: () -> Unit,
    navigateToNoteList: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = onDrawerOpenRequest
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
//                Text(text = "Kalender Adat")
                Image(
                    painter = painterResource(id = R.drawable.header),
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
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

@Composable
fun CalendarAppDrawer(
    redirectTo: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 32.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.logo_desa), contentDescription = null)
        }

        NavigationDrawerItem(
            icon = {
                Icon(imageVector = Icons.Filled.Home, contentDescription = null)
            },
            label = {
                Text("Beranda")
            },
            selected = true,
            onClick = { redirectTo(HomeDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(painter = painterResource(id = R.drawable.ic_note), contentDescription = null)
            },
            label = {
                Text("Catatan")
            },
            selected = false,
            onClick = { redirectTo(NoteListDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            },
            label = {
                Text("Info Aplikasi")
            },
            selected = false,
            onClick = { redirectTo(InfoDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Preview
@Composable
fun CalendarAppBarPreview() {
    Scaffold(
        topBar = {
            CalendarAppBar(
                onDrawerOpenRequest = {},
                navigateToNoteList = {}
            )
        },
    ) { innerPadding ->
        innerPadding
    }
}