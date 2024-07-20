package com.dedan.kalenderadat.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.kalenderadat.AppViewModelProvider
import com.dedan.kalenderadat.CalendarAppBar
import com.dedan.kalenderadat.CalendarDrawer
import com.dedan.kalenderadat.R
import com.dedan.kalenderadat.ui.component.BottomSheet
import com.dedan.kalenderadat.ui.component.CalendarHeader
import com.dedan.kalenderadat.ui.component.CalendarLayout
import com.dedan.kalenderadat.ui.component.ClickOnDateGuide
import com.dedan.kalenderadat.ui.component.ShowEventBrief
import com.dedan.kalenderadat.ui.component.ShowFullDateDetail
import com.dedan.kalenderadat.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToNoteEditor: (date: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val noteState by viewModel.noteState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CalendarDrawer()
        },
        modifier = modifier
    ) {
        Scaffold(
            topBar = {
                CalendarAppBar()
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            BoxWithConstraints(modifier = modifier.padding(paddingValues)) {
                val calendarHeight by remember {
                    derivedStateOf {
                        (maxHeight.value * 0.6f).dp
                    }
                }
                val bottomSheetHeight by remember(uiState.bottomSheetExpand) {
                    derivedStateOf {
                        if (uiState.bottomSheetExpand)
                            (maxHeight.value * 0.85f).dp
                        else
                            (maxHeight.value * 0.38f).dp
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calendarHeight)
                ) {
                    CalendarHeader(
                        currentDate = uiState.currentDate,
                        onDateChange = { viewModel.setCurrentDate(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CalendarLayout(
                        currentDate = uiState.currentDate,
                        dates = uiState.dates,
                        selectedDate = uiState.selectedDate,
                        purtimUiState = viewModel.purtimUiState,
                        holidayUiState = viewModel.holidayUiState,
                        dateEventUiState = viewModel.dateEventUiState,
                        onDateSelected = { viewModel.setOpenDetailDate(it) },
                        onRefreshClick = { viewModel.fetchDates(uiState.currentDate, true) }
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BottomSheet(
                        collapsable = uiState.bottomSheetExpand,
                        onCollapseRequest = { viewModel.setBottomSheetExpand(false) },
                        modifier = Modifier
//                            .animateContentSize()
                            .height(bottomSheetHeight)
                    ) {
                        when {
                            uiState.selectedDate == null -> ClickOnDateGuide()
                            !uiState.bottomSheetExpand -> ShowEventBrief(
                                selectedDate = uiState.selectedDate!!,
                                eventDetailUiState = viewModel.eventDetailUiState,
                                onExpandClick = { viewModel.setBottomSheetExpand(true) }
                            )
                            uiState.bottomSheetExpand -> ShowFullDateDetail(
                                selectedDate = uiState.selectedDate!!,
                                navigateToNoteEditor = navigateToNoteEditor,
                                noteState = noteState,
                                eventDetailUiState = viewModel.eventDetailUiState
                            )
                        }
                    }
                }
            }
        }

    }
}