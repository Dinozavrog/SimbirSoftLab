package com.example.simbirsoft.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.simbirsoft.R
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.presentation.presenter.CreateNoteViewModel
import com.example.simbirsoft.presentation.utils.getMonthNumber
import com.example.simbirsoft.ui.theme.Background
import com.example.simbirsoft.ui.theme.DayColor
import com.example.simbirsoft.ui.theme.EditTextBackground
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: CreateNoteViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    CreateNoteContent(viewState = state.value, eventHandler = viewModel::event)
    CreateNoteActions(navController = navController, viewAction = action)

}

@Composable
fun CreateNoteContent(
    viewState: CreateNoteViewModel.CreateNoteViewState?,
    eventHandler: ((CreateNoteViewModel.CreateNoteEvent) -> Unit)?
) {
    val textState = remember {
        mutableStateOf("")
    }
    var description = remember { mutableStateOf("") }
    var title = remember {
        mutableStateOf("")
    }
    var pickerDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedStartTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    var pickedFinishTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMMM dd yyyy")
                .format(pickerDate)
        }
    }
    val startTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedStartTime)
        }
    }
    val finishTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedFinishTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeStartDialogState = rememberMaterialDialogState()
    val timeFinishDialogState = rememberMaterialDialogState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Background)) {
        Text(text = "New note",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 64.dp, 0.dp, 0.dp),
            color = Color.White,
            fontSize = 32.sp,
            textAlign = TextAlign.Center)

        TransparentHintTextField(
            hint = "Enter the title",
            value = title.value,
            onValueChange = {title.value = it})
        TransparentHintTextField(
            hint = "Enter a description",
            value = description.value,
            onValueChange ={ description.value = it },
        )
        Spacer(modifier = Modifier.height(56.dp))
        Column(
            modifier = Modifier
                .padding(16.dp, 24.dp, 16.dp, 0.dp)
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = EditTextBackground,
                    shape = RoundedCornerShape(8.dp)
                ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Choose date",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(24.dp, 12.dp, 16.dp, 0.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 24.dp, 0.dp)
                        .clickable {
                            dateDialogState.show()
                        }
                        .size(width = 170.dp, height = 40.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = formattedDate,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Start time",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(24.dp, 12.dp, 16.dp, 0.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 24.dp, 0.dp)
                        .clickable {
                            timeStartDialogState.show()
                        }
                        .size(width = 170.dp, height = 40.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = startTime,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Finish time",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(24.dp, 12.dp, 16.dp, 0.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 24.dp, 0.dp)
                        .clickable {
                            timeFinishDialogState.show()
                        }
                        .size(width = 170.dp, height = 40.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = finishTime,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    eventHandler?.invoke(CreateNoteViewModel.CreateNoteEvent.onSaveNoteClick(
                        NoteModel(
                            0,
                            title.value,
                            description.value,
                            LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("MMMM dd yyyy")).month.toString(),
                            LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("MMMM dd yyyy")).dayOfMonth,
                            pickedStartTime.hour,
                            pickedStartTime.minute,
                            pickedFinishTime.hour,
                            pickedFinishTime.hour
                        )
                    ))

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = DayColor),
                modifier = Modifier
                    .height(48.dp)
                    .width(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Save", color = Color.White, fontSize = 16.sp)
            }
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "OK") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Choose a date"
        ) {
            pickerDate = it
        }
    }
    MaterialDialog(
        dialogState = timeStartDialogState,
        buttons = {
            positiveButton(text = "OK") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Choose a date",
        ) {
            pickedStartTime = it
        }
    }
    MaterialDialog(
        dialogState = timeFinishDialogState,
        buttons = {
            positiveButton(text = "OK") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Choose a date",
        ) {
            pickedFinishTime = it
        }
    }
}

@Composable
fun CreateNoteActions(
    navController: NavController,
    viewAction: CreateNoteViewModel.CreateNoteAction?
) {
    LaunchedEffect(viewAction) {
        when(viewAction) {
            null -> Unit
            is CreateNoteViewModel.CreateNoteAction.Navigate -> {
                navController.navigate("home")
            }
        }
    }
}

@Composable
fun TransparentHintTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val isHintVisible = value.isEmpty()

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(16.dp, 24.dp, 16.dp, 0.dp)
            .fillMaxWidth()
            .background(
                color = EditTextBackground,
                shape = RoundedCornerShape(8.dp)
            ),
        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            if (isHintVisible) {
                Text(
                    text = hint,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
fun showCreateScreen() {
    CreateNoteContent(viewState = null,  eventHandler = null)
}

@Preview
@Composable
fun PreviewProgressBar() {
    Image(
        painter = painterResource(id = R.drawable.loader),
        contentDescription = "image description",
        contentScale = ContentScale.None
    )
}




