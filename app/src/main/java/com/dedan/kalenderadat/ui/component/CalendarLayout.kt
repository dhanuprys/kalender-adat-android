package com.dedan.kalenderadat.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dedan.kalenderadat.data.DateEventUiState
import com.dedan.kalenderadat.model.DateEvent
import com.dedan.kalenderadat.util.getWuku
import com.dedan.kalenderadat.util.safeSlice
import com.dedan.kalenderadat.util.translateColorString
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

@Composable
fun CalendarLayout(
    currentDate: LocalDate,
    dates: List<LocalDate>,
    dateEventUiState: DateEventUiState,
    modifier: Modifier = Modifier,
    selectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit = {},
    onRefreshClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier = modifier.animateContentSize()) {
        val cellHeight = ((maxHeight.value) / 7).dp

        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.padding(horizontal = 16.dp),
            userScrollEnabled = false
        ) {
            var dateIndex: Int = 0
            var wukuIndex: Int = 0
            var dayIndex: Int = 0

            items(56) { item ->
                if (item == 0) {
                    LoadStatusCell(
                        isLoading = dateEventUiState is DateEventUiState.Loading,
                        onRefreshClick = onRefreshClick,
                        modifier = Modifier.height(cellHeight)
                    )
                    return@items
                }

                if (item % 8 == 0) {
                    WukuCell(
                        date = dates[wukuIndex * 7],
                        modifier = Modifier.height(cellHeight)
                    )
                    wukuIndex++
                    return@items
                }

                if (item >= 1 && item <= 7) {
                    DayCell(
                        dayIndex = dayIndex,
                        modifier = Modifier.height(cellHeight)
                    )
                    dayIndex++
                    return@items
                }

                DateCell(
                    currentDate = currentDate,
                    date = dates[dateIndex],
                    isSelected = selectedDate == dates[dateIndex],
                    onClicked = onDateSelected,
                    events = if (dateEventUiState is DateEventUiState.Success)
                        dateEventUiState.data.find {
                            it.date == dates[dateIndex].format(
                                DateTimeFormatterBuilder().appendPattern(
                                    "yyyy-MM-dd"
                                ).toFormatter()
                            )
                        }
                    else null,
                    modifier = Modifier
                        .height(cellHeight)
                        .fillMaxWidth()
                )
                dateIndex++
            }
        }
    }
}

@Composable
fun LoadStatusCell(
    isLoading: Boolean,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(20.dp)
                    .offset(0.dp, 8.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun WukuCell(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            getWuku(date).safeSlice(0..3)
        )
    }
}

@Composable
fun DayCell(
    dayIndex: Int,
    modifier: Modifier = Modifier
) {
    val days = listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(days[dayIndex])
    }
}

@Composable
fun DateCell(
    currentDate: LocalDate,
    date: LocalDate,
    events: DateEvent?,
    isSelected: Boolean = false,
    onClicked: (LocalDate) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val inMonth = date.monthValue == currentDate.monthValue
            && date.year == currentDate.year

    Box(
        modifier = modifier.background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant
                else MaterialTheme.colorScheme.surface
            ).clickable { onClicked(date) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (events != null) {
                MultiSegmentCircle(
                    modifier = Modifier.size(31.dp),
                    colors = events.colors.map { translateColorString(it) }
                ) {
                    Text(
                        date.dayOfMonth.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Text(
                    date.dayOfMonth.toString(),
                    color = if (date.dayOfWeek.value == 7 && inMonth) Color.Red
                        else if (inMonth) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}