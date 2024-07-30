package com.dedan.kalenderadat.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dedan.kalenderadat.R
import com.dedan.kalenderadat.data.EventDetailUiState
import com.dedan.kalenderadat.data.NoteItemUiState
import com.dedan.kalenderadat.model.EventDetail
import com.dedan.kalenderadat.util.translateColorString
import com.dedan.kalenderadat.util.translateDayIndexToBalineseDay
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder


@Composable
fun ShowFullDateDetail(
    selectedDate: LocalDate,
    eventDetailUiState: EventDetailUiState,
    navigateToNoteEditor: (date: String) -> Unit,
    noteState: NoteItemUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize()
    ) {
        EventDetailHeader(date = selectedDate)
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            when (eventDetailUiState) {
                is EventDetailUiState.Loading ->
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                is EventDetailUiState.Success -> {
                    if (eventDetailUiState.data.isEmpty()) {
                        NoteCard(
                            navigateToNoteEditor = navigateToNoteEditor,
                            noteState = noteState,
                            date = selectedDate,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            item {
                                NoteCard(
                                    navigateToNoteEditor = navigateToNoteEditor,
                                    noteState = noteState,
                                    date = selectedDate,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                )
                            }

                            items(eventDetailUiState.data.size) {
                                val item = eventDetailUiState.data[it]

                                EventCardItem(
                                    event = item,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 100.dp)
                                )
                            }
                        }
                    }
                }
                is EventDetailUiState.Error -> {
                    Text("Error")
                }
            }
        }
    }
}

@Composable
fun ShowEventBrief(
    selectedDate: LocalDate,
    eventDetailUiState: EventDetailUiState,
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize()
    ) {
        EventDetailHeader(date = selectedDate)
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (eventDetailUiState) {
                is EventDetailUiState.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is EventDetailUiState.Success -> {
                    if (eventDetailUiState.data.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Tidak ada acara pada tanggal ini",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Column {
                            LazyColumn {
                                val itemShow = minOf(eventDetailUiState.data.size, 2)
                                items(itemShow) {
                                    val item = eventDetailUiState.data[it]
                                    TextWithBullet(text = item.title)
                                }
                            }
                            if (eventDetailUiState.data.size > 2) {
                                TextWithBullet(text = "...")
                            }
                        }
                    }
                }

                is EventDetailUiState.Error -> {
                    Text("Error")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onExpandClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buka Selengkapnya")
        }
    }
}

@Composable
fun ClickOnDateGuide(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 20.dp)
    ) {
        Text(
            text = "Pilih salah satu tanggal",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EventDetailHeader(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    val balineseDate by remember(date) {
        derivedStateOf {
            translateDayIndexToBalineseDay(date.dayOfWeek.value) + date.format(
                DateTimeFormatterBuilder()
                    .appendPattern(", dd MMMM yyyy")
                    .toFormatter()
            )
        }
    }

    Text(
        text = balineseDate,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun EventCardItem(
    event: EventDetail,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            if (event.imageUrl != null) {
                AsyncImage(
                    model = "https://adat.suryamahendra.com/storage/" + event.imageUrl,
                    fallback = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = event.description ?: "Deskripsi tidak tersedia",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategoryName(
                color = translateColorString(event.categoryColor),
                text = event.categoryName
            )
//            Text(
//                text = "Terakhir diubah pada xxxx",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                textAlign = TextAlign.End,
//                modifier = Modifier.fillMaxWidth()
//            )
        }
    }
}