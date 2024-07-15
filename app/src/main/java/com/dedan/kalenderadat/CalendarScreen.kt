package com.dedan.kalenderadat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dedan.kalenderadat.ui.CalendarViewModel
import com.dedan.kalenderadat.ui.screen.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.kalenderadat.model.AppRoutes
import com.dedan.kalenderadat.model.navigationItemList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarApp(
    viewModel: CalendarViewModel = viewModel(
        factory = CalendarViewModel.Factory
    ),
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    navigationItemList.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item.title) },
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            selected = false,
                            onClick = {
                                item.onClick(navController)
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                            }
                        },
                        title = {
                            Text(text = "Kalender Bali")
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                paddingValues
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.Home.name
                ) {
                    composable(AppRoutes.Home.name) {
                        HomeScreen(
                            uiState = uiState,
                            dateEventUiState = viewModel.dateEventUiState,
                            eventDetailUiState = viewModel.eventDetailUiState,
                            onDateChange = { viewModel.setCurrentDate(it) },
                            onDateSelected = { viewModel.setOpenDetailDate(it) },
                            onExpandClick = { viewModel.setBottomSheetExpand(true) },
                            onCollapseClick = { viewModel.setBottomSheetExpand(false) },
                            onRefreshClick = { viewModel.fetchDates(uiState.currentDate) },
                            modifier = Modifier.padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            )
                        )
                    }
                    composable(AppRoutes.Info.name) {
                        Text("Info")
                    }
                }
            }
        }
    }
}