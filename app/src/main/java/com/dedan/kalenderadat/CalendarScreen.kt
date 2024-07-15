package com.dedan.kalenderadat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dedan.kalenderadat.ui.CalendarViewModel
import com.dedan.kalenderadat.ui.screen.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel

enum class CalendarScreen {
    Home
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarApp(
    viewModel: CalendarViewModel = viewModel(
        factory = CalendarViewModel.Factory
    ),
    navController: NavHostController = rememberNavController()
) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
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
                startDestination = CalendarScreen.Home.name
            ) {
                composable(CalendarScreen.Home.name) {
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
            }
        }
    }
}