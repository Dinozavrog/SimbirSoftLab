package com.example.simbirsoft.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.simbirsoft.presentation.presenter.HomeViewModel
import com.example.simbirsoft.ui.theme.Background
import com.example.simbirsoft.ui.theme.DayColor
import kotlinx.coroutines.launch
import com.example.simbirsoft.R
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.ui.theme.EditTextBackground
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    HomeContent(viewState = state.value, eventHandler = viewModel::event)
    HomeActions(navController = navController, viewAction = action)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeContent(
    viewState: HomeViewModel.HomeViewState,
    eventHandler: (HomeViewModel.HomeEvent) -> Unit
) {
    val currentMonth = remember { mutableStateOf("July") }
    val currentDay = remember { mutableStateOf("7") }
    var calendarInputList by remember {
        mutableStateOf(createCalendarList(currentMonth.value))
    }
    var clickedCalendarElement by remember {
        mutableStateOf<CalendarInput?>(null)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SimbirSoft") },
                backgroundColor = EditTextBackground,
                contentColor = Color.White,
                actions = {
                    IconButton(
                        onClick = { eventHandler.invoke(HomeViewModel.HomeEvent.OnCreateNoteClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Button"
                        )
                    }
                },
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(Modifier.fillMaxSize()) {
                Calendar(
                    calendarInput = calendarInputList,
                    onDayClick = { day ->
                        clickedCalendarElement = calendarInputList.first { it.day == day }
                    },
                    month = currentMonth.value,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.3f),
                    eventHandler = eventHandler
                )
                if (viewState.noteList == null) {
                    eventHandler.invoke(HomeViewModel.HomeEvent.OnDayClick(day = currentDay.value.toString(), month = currentMonth.value))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                }
                else {
                    HourList(viewState = viewState, eventHandler = eventHandler)
                }
            }
            IconButton(
                onClick = {
                    currentMonth.value = getNextMonth(currentMonth.value)
                    calendarInputList = createCalendarList(currentMonth.value)
                },
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .padding(bottom = 40.dp, start = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow Button",
                    tint = Color.White
                )
            }
        }
    }
}




private const val CALENDAR_ROWS = 5
private const val CALENDAR_COLUMNS = 7

@Composable
fun Calendar(
    modifier: Modifier,
    calendarInput: List<CalendarInput>,
    onDayClick: (Int) -> Unit,
    strokeWidth: Float = 8f,
    month: String,
    eventHandler: (HomeViewModel.HomeEvent) -> Unit,
) {

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }
    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var animationRadius by remember {
        mutableStateOf(0f)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = month,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 40.sp
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val column =
                                (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1
                            val day = column + (row - 1) * CALENDAR_COLUMNS
                            if (day <= calendarInput.size) {
                                onDayClick(day)
                                eventHandler.invoke(
                                    HomeViewModel.HomeEvent.OnDayClick(
                                        month,
                                        day.toString()
                                    )
                                )
                                Log.e("day", day.toString() + " " + month)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }

                        }
                    )
                }
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth,canvasHeight)
            val ySteps = canvasHeight/ CALENDAR_ROWS
            val xSteps = canvasWidth/ CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
            val row = (clickAnimationOffset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1

            val path = Path().apply {
                moveTo((column-1)*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,row*ySteps)
                lineTo((column-1)*xSteps,row*ySteps)
                close()
            }
            clipPath(path){
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(DayColor.copy(0.8f), DayColor.copy(0.2f)),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    ),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }

            drawRoundRect(
                DayColor,
                cornerRadius = CornerRadius(25f, 25f),
                style = Stroke(
                    width = strokeWidth
                )
            )
            for (i in 1 until CALENDAR_ROWS) {
                drawLine(
                    color = DayColor,
                    start = Offset(0f, ySteps*i),
                    end = Offset(canvasWidth, ySteps*i),
                    strokeWidth = strokeWidth

                )
            }
            for (i in 1 until CALENDAR_COLUMNS) {
                drawLine(
                    color = DayColor,
                    start = Offset(xSteps*i, 0f),
                    end = Offset(xSteps*i, canvasHeight),
                    strokeWidth = strokeWidth

                )
            }
            val textHeight = 17.dp.toPx()
            for (i in calendarInput.indices) {
                val textPositionX = xSteps * (i % CALENDAR_COLUMNS) + strokeWidth
                val textPositionY = (i / CALENDAR_COLUMNS) * ySteps + textHeight + strokeWidth / 2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${i + 1}",
                        textPositionX,
                        textPositionY,
                        Paint().apply {
                            textSize = textHeight
                            color = Color.White.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeActions(
    navController: NavController,
    viewAction: HomeViewModel.HomeAction?
) {
    val prefix = stringResource(id = R.string.details_prefix)
    LaunchedEffect(viewAction) {
        when(viewAction) {
            null -> Unit
            is HomeViewModel.HomeAction.Navigate -> {
                navController.navigate(prefix + viewAction.noteId)
            }
            is HomeViewModel.HomeAction.NavigateToAddScreen -> {
                navController.navigate("add")
            }
        }
    }
}

private fun createCalendarList(month: String): List<CalendarInput> {
    val daysInMonth = when (month) {
        "January", "March", "May", "July", "August", "October", "December" -> 31
        "April", "June", "September", "November" -> 30
        "February" -> 28
        else -> throw IllegalArgumentException("Invalid month: $month")
    }

    val calendarInput = mutableListOf<CalendarInput>()
    for (i in 1..daysInMonth) {
        calendarInput.add(
            CalendarInput(
                i,
                toDoList = listOf(
                    "Day $i:",
                    "2 p.m. buying groceries",
                    "4 p.m. meeting"
                )
            )
        )
    }
    return calendarInput
}

data class CalendarInput(
    val day: Int,
    val toDoList: List<String> = emptyList()
)

fun getNextMonth(currentMonth: String): String {
    val monthList = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val currentMonthIndex = monthList.indexOf(currentMonth)
    val nextMonthIndex = (currentMonthIndex + 1) % monthList.size

    return monthList[nextMonthIndex]
}

@Composable
fun HourList(
    viewState: HomeViewModel.HomeViewState,
    eventHandler: (HomeViewModel.HomeEvent) -> Unit,
) {
    val hours = List(24) { hour -> hour.toString() }
    val displayedHours = mutableSetOf<String>()

    val notes = viewState.noteList
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
    ) {
        itemsIndexed(hours) { index, hour ->
            val matchingNote = findMatchingNoteForHour(hour, notes)
            val matchingHours = if (matchingNote != null) findMatchingHours(hour, matchingNote) else emptyList()
            val isFirstHour = index == 0
            val isDifferentNote = matchingNote != null && matchingNote != findMatchingNoteForHour(hours[index - 1], notes)

            if (matchingNote != null && (isFirstHour || isDifferentNote) && hour !in displayedHours) {
                HourItemColumn(hours = matchingHours, note = matchingNote)
                displayedHours.addAll(matchingHours)
            } else if (hour !in displayedHours){
                HourItem(hour = hour, note = matchingNote)
            }
        }
    }
}


//@Composable
//fun HourList(notes: List<NoteModel>?) {
//    val hours = List(24) { hour -> hour.toString() }
//    val displayedHours = mutableSetOf<String>()
//
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Background)
//    ) {
//        itemsIndexed(hours) { index, hour ->
//            val matchingNote = findMatchingNoteForHour(hour, notes)
//            val matchingHours = if (matchingNote != null) findMatchingHours(hour, matchingNote) else emptyList()
//            val isFirstHour = index == 0
//            val isDifferentNote = matchingNote != null && matchingNote != findMatchingNoteForHour(hours[index - 1], notes)
//
//            if (matchingNote != null && (isFirstHour || isDifferentNote) && hour !in displayedHours) {
//                HourItemColumn(hours = matchingHours, note = matchingNote)
//                displayedHours.addAll(matchingHours)
//            } else if (hour !in displayedHours){
//                HourItem(hour = hour, note = matchingNote)
//            }
//        }
//    }
//}


private fun findMatchingNoteForHour(hour: String, notes: List<NoteModel>?): NoteModel? {
    return notes?.find { note ->
        hour.toInt() >= note.timeStart && hour.toInt() < note.timeFinish - 1
    }
        ?: notes?.find { note ->
            hour.toInt() == note.timeFinish && hour.toInt() == note.timeFinish
        }
}

private fun findMatchingHours(hour: String, note: NoteModel): List<String> {
    return List(note.timeFinish - note.timeStart + 1) { index ->
        (note.timeStart + index).toString().padStart(2, '0')
    }
}


@Composable
fun HourItemColumn(hours: List<String>, note: NoteModel) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Background)
    ) {
        Column(modifier = Modifier.width(48.dp)) {
            for (hour in hours) {
                Text(
                    text = hour,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
        Box(modifier = Modifier
            .background(
                color = EditTextBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .weight(1f)
            .height((hours.size * 32).dp),
            contentAlignment = Alignment.Center) {
            Text(
                text = note.name,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center),
                color = Color.White
            )
        }
    }
}


@Composable
fun HourItem(hour: String, note: NoteModel?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = hour.padStart(2, '0'),
            modifier = Modifier.width(48.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        if (note != null) {
            Box(
                modifier = Modifier
                    .background(
                        color = EditTextBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = note.name,
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(textAlign = TextAlign.Start),
                    color = Color.White
                )
            }
        } else {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .padding(top = 1.dp)
                    .align(Alignment.CenterVertically),
                color = Color.White,
            )
        }
    }
}


//@Preview
//@Composable
//fun showList() {
//    HourList(notes = listOf(NoteModel(1, "hdhdhd", "hdhdh", "September", 1, 12, 14, 20, 20),
//        NoteModel(1, "hdhdhd", "hdhdh", "September", 1, 8, 10, 20, 20),
//        NoteModel(1, "hdhdhd", "hdhdh", "September", 1, 1, 1,  1, 20)))
//}













