package com.example.simbirsoft.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.presentation.presenter.NoteDetailsViewModel
import com.example.simbirsoft.ui.theme.Background


@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteDetailsViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    NoteDetailContent(
        noteId = noteId,
        viewState = state.value,
        eventHandler = viewModel::event
    )
}

@Composable
fun NoteDetailContent(
    noteId: Int,
    viewState: NoteDetailsViewModel.DetailsViewState,
    eventHandler: (NoteDetailsViewModel.DetailsEvent) -> Unit
) {
    if (viewState.note == null) {
        eventHandler.invoke(NoteDetailsViewModel.DetailsEvent.OnLoadNote(noteId))
        if (viewState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
    else {
        NoteDetailScreen(note = viewState.note)
    }
}
@Composable
fun NoteDetailScreen(
    note: NoteModel,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Background),
    ) {
        Text(text = note.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 64.dp, 16.dp, 0.dp),
        color = Color.White,
        fontSize = 32.sp,
        textAlign = TextAlign.Center)
        Text(text = "Date: ${note.month} , ${note.day}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center)
        Text(text = "Time: ${note.timeStart}:${note.minutesStart} - ${note.timeFinish}:${note.minutesFinish}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center)
        Text(text = note.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 16.dp, 0.dp),
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun show() {
    NoteDetailScreen(note = NoteModel(1, "Note", "jfjdjjdjhdfvhjdfhjdhdjsdshjdshsdjhsdhjsdjhsjhsjhsdhjsdhjdshjdshjdshjdshjhjdshjsdhjhdshjsdhdshhjdshjdshjdshjdshjsdhjhjdshjhjsdhjsdhjsdhjdshjdshjshjdshj", "September", 10, 10, 10, 10, 10))
}