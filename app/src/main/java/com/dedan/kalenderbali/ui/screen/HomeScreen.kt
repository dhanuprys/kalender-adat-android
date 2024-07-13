package com.dedan.kalenderbali.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dedan.kalenderbali.data.CalendarUiState
import com.dedan.kalenderbali.data.DateEventUiState
import com.dedan.kalenderbali.data.EventDetailUiState
import com.dedan.kalenderbali.ui.component.BottomSheet
import com.dedan.kalenderbali.ui.component.CalendarHeader
import com.dedan.kalenderbali.ui.component.CalendarLayout
import com.dedan.kalenderbali.ui.component.ClickOnDateGuide
import com.dedan.kalenderbali.ui.component.ShowEventBrief
import com.dedan.kalenderbali.ui.component.ShowFullDateDetail
import com.dedan.kalenderbali.ui.component.TextWithBullet
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

@Composable
fun HomeScreen(
    uiState: CalendarUiState,
    dateEventUiState: DateEventUiState,
    eventDetailUiState: EventDetailUiState,
    onDateChange: (LocalDate) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onExpandClick: () -> Unit,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val calendarHeight = (maxHeight.value * 60) / 100
        val bottomSheetHeight = (if (uiState.bottomSheetExpand) (maxHeight.value * 90) / 100
        else (maxHeight.value * 35) / 100).dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(calendarHeight.dp)
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
                modifier = Modifier
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
                        eventDetailUiState = eventDetailUiState
                    )
                }
            }
        }

    }
}