package com.dedan.kalenderadat.ui.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dedan.kalenderadat.data.EventDetailUiState
import com.dedan.kalenderadat.util.translateColorString
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder


@Composable
fun ShowFullDateDetail(
    selectedDate: LocalDate,
    eventDetailUiState: EventDetailUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize()
    ) {
        EventDetailHeader(date = selectedDate)
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            when (eventDetailUiState) {
                is EventDetailUiState.Loading ->
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                is EventDetailUiState.Success -> {
                    if (eventDetailUiState.data.isEmpty()) {
                        Text("No Event")
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(eventDetailUiState.data.size) {
                                val item = eventDetailUiState.data[it]

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 100.dp),
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        if (item.imageUrl != null) {
                                            AsyncImage(
                                                model = "https://adat.suryamahendra.com/storage/" + item.imageUrl,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxWidth()
                                                    .height(150.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                        }

                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = item.description ?: "Deskripsi tidak tersedia",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        CategoryName(
                                            color = translateColorString(item.categoryColor),
                                            text = item.categoryName
                                        )
                                        Text(
                                            text = "Terakhir diubah pada xxxx",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
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
    Column(modifier = modifier
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
                        Text("No Event")
                    } else {
                        Column {
                            LazyColumn {
                                items(2) {
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
    Text(
        text = date.format(
            DateTimeFormatterBuilder()
                .appendPattern("cccc, dd MMMM yyyy")
                .toFormatter()
        ),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth()
    )
}