package com.dedan.kalenderadat.ui.screen.notelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dedan.kalenderadat.R
import com.dedan.kalenderadat.data.NoteItem
import com.dedan.kalenderadat.ui.navigation.NavigationDestination
import com.dedan.kalenderadat.ui.theme.KalenderBaliTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.kalenderadat.AppViewModelProvider
import com.dedan.kalenderadat.util.DateUtil
import com.dedan.kalenderadat.util.translateDayIndexToBalineseDay
import java.time.format.DateTimeFormatterBuilder

object NoteListDestination : NavigationDestination {
    override val route = "note_list"
    override val titleRes =  R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val noteListUiState by viewModel.notes.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Catatan",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NoteListBody(
            noteListUiState = noteListUiState,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun NoteListBody(
    noteListUiState: NoteListUiState,
    modifier: Modifier = Modifier
) {
    val notes = noteListUiState.notes

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(items = notes) {
            NoteCard(note = it)
        }
    }
}

@Preview
@Composable
fun NoteListBodyPreview() {
    KalenderBaliTheme {
        NoteListBody(
            noteListUiState = NoteListUiState(
                notes = listOf(
                    NoteItem(id = 1, content = "Hello 1", date = "2024-01-01"),
                    NoteItem(id = 2, content = "Hello 2", date = "2024-01-02"),
                    NoteItem(id = 3, content = "Hello 3", date = "2024-01-03"),
                    NoteItem(id = 4, content = "Hello 4", date = "2024-01-04"),
                    NoteItem(id = 5, content = "Hello 5", date = "2024-01-05")
                )
            )
        )
    }
}

@Composable
fun NoteCard(
    note: NoteItem,
    modifier: Modifier = Modifier
) {
    val dateString by remember(note) {
        derivedStateOf {
            DateUtil.parseNormalizedDate(note.date).format(
                DateTimeFormatterBuilder().appendPattern("dd MMMM yyyy").toFormatter()
            )
        }
    }
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.content)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                dateString,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun NoteCardPreview() {
    KalenderBaliTheme {
        NoteCard(
            note = NoteItem(
                id = 0,
                content = "Hello",
                date = "2024-29-33"
            )
        )
    }
}