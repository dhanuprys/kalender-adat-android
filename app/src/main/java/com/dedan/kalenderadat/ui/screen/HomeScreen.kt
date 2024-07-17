package com.dedan.kalenderadat.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dedan.kalenderadat.data.CalendarUiState
import com.dedan.kalenderadat.data.DateEventUiState
import com.dedan.kalenderadat.data.EventDetailUiState
import com.dedan.kalenderadat.data.HolidayUiState
import com.dedan.kalenderadat.data.NoteItemUiState
import com.dedan.kalenderadat.data.PurtimUiState
import com.dedan.kalenderadat.ui.component.BottomSheet
import com.dedan.kalenderadat.ui.component.CalendarHeader
import com.dedan.kalenderadat.ui.component.CalendarLayout
import com.dedan.kalenderadat.ui.component.ClickOnDateGuide
import com.dedan.kalenderadat.ui.component.ShowEventBrief
import com.dedan.kalenderadat.ui.component.ShowFullDateDetail
import java.time.LocalDate

@Composable
fun HomeScreen(
    uiState: CalendarUiState,
    dateEventUiState: DateEventUiState,
    purtimUiState: PurtimUiState,
    holidayUiState: HolidayUiState,
    eventDetailUiState: EventDetailUiState,
    noteState: NoteItemUiState,
    onDateChange: (LocalDate) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onExpandClick: () -> Unit,
    onCollapseClick: () -> Unit,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val calendarHeight by remember {
            derivedStateOf {
                (maxHeight.value * 0.6f).dp
            }
        }
        val bottomSheetHeight by remember(uiState.bottomSheetExpand) {
            derivedStateOf {
                if (uiState.bottomSheetExpand)
                        (maxHeight.value * 0.80f).dp
                else
                        (maxHeight.value * 0.35f).dp
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(calendarHeight)
        ) {
            CalendarHeader(
                currentDate = uiState.currentDate,
                onDateChange = onDateChange,
                modifier = Modifier.fillMaxWidth()
            )
            CalendarLayout(
                currentDate = uiState.currentDate,
                dates = uiState.dates,
                selectedDate = uiState.selectedDate,
                purtimUiState = purtimUiState,
                holidayUiState = holidayUiState,
                dateEventUiState = dateEventUiState,
                onDateSelected = onDateSelected,
                onRefreshClick = onRefreshClick
            )
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            BottomSheet(
                collapsable = uiState.bottomSheetExpand,
                onCollapseRequest = onCollapseClick,
                modifier = Modifier
                    .animateContentSize()
                    .height(bottomSheetHeight)
            ) {
                when {
                    uiState.selectedDate == null -> ClickOnDateGuide()
                    !uiState.bottomSheetExpand -> ShowEventBrief(
                        selectedDate = uiState.selectedDate,
                        eventDetailUiState = eventDetailUiState,
                        onExpandClick = onExpandClick
                    )
                    uiState.bottomSheetExpand -> ShowFullDateDetail(
                        selectedDate = uiState.selectedDate,
                        noteState = noteState,
                        eventDetailUiState = eventDetailUiState
                    )
                }
            }
        }

    }
}