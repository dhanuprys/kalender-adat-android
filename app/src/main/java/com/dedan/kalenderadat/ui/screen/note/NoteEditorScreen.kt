package com.dedan.kalenderadat.ui.screen.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedan.kalenderadat.AppViewModelProvider
import com.dedan.kalenderadat.R
import com.dedan.kalenderadat.ui.navigation.NavigationDestination
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.kalenderadat.ui.theme.KalenderBaliTheme
import kotlinx.coroutines.launch

object NoteEditorDestination : NavigationDestination {
    override val route = "note"
    override val titleRes = R.string.app_name
    const val dateArg = "date"
    val routeWithArgs = "$route/{$dateArg}"
}

@Composable
fun NoteEditorScreen(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteEditorViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        NoteEditorBody(
            uiState = viewModel.uiState,
            onValueChange = viewModel::updateNoteContent,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateBack()
                }
            },
            onCancelClick = navigateUp,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NoteEditorBody(
    uiState: NoteEditorUiState,
    onValueChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        NoteForm(
            noteValue = uiState.note?.content,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ActionButton(
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick
            )
        }
    }
}

@Composable
fun NoteForm(
    noteValue: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = 500
) {
    val currentLength = noteValue?.length ?: 0
    val isFull = currentLength >= maxLength

    Column {
        TextField(
            value = noteValue ?: "",
            onValueChange = {
                if (!isFull) {
                    onValueChange(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "${currentLength.toString()} / ${maxLength.toString()}"
            )

            if (isFull) {
                Text(
                    text = "(Sudah maksimal)",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        Button(
            onClick = onCancelClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("Batal")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onSaveClick
        ) {
            Text("Simpan")
        }
    }
}

@Preview
@Composable
fun  NoteEditorScreenPreview() {
    KalenderBaliTheme {
        NoteEditorScreen(navigateBack = {}, navigateUp = {})
    }
}