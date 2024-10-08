package com.dedan.kalenderadat.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dedan.kalenderadat.data.NoteItemUiState
import com.dedan.kalenderadat.util.DateUtil
import java.time.LocalDate

@Composable
fun NoteCard(
    noteState: NoteItemUiState,
    navigateToNoteEditor: (date: String) -> Unit,
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    if (noteState.available) {
        NoteContent(
            content = noteState.note!!.content,
            onEditClick = {
                navigateToNoteEditor(
                    date.format(DateUtil.normalizeDateFormat())
                )
            },
            modifier = modifier
        )
    } else {
        NoteCreateButton(
            onClick = {
                navigateToNoteEditor(
                    date.format(DateUtil.normalizeDateFormat())
                )
            },
            modifier = modifier
        )
    }
//    NoteCreateButton(modifier = modifier)
//    NoteContent(modifier = modifier)
}

@Composable
fun NoteContent(
    content: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Catatann",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Button(
                    onClick = onEditClick,
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = content)
        }
    }
}

@Composable
fun NoteCreateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tambahkan catatan")
        }
    }
}