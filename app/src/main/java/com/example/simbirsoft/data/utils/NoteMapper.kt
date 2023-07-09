package com.example.simbirsoft.data.utils

import com.example.simbirsoft.data.local.model.NoteLocalModel
import com.example.simbirsoft.domain.model.NoteModel
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun NoteLocalModel.toNoteModel(): NoteModel {
//    val date = getHours(dateStart = dateStart, dateFinish = dateFinish)
    return NoteModel(
        id = id,
        name = name,
        description = description,
        month =  month,
        day = day,
        timeStart = timeStart,
        timeFinish = timeFinish,
        minutesStart = minutesStart,
        minutesFinish = minutesFinish
    )
}

private fun getHours(dateStart: Timestamp, dateFinish: Timestamp): List<Any> {
    val dateFirst = Date(dateStart.time)
    val calendar = Calendar.getInstance()
    calendar.time = dateFirst
    val dateSecond = Date(dateFinish.time)
    val calendarSecond = Calendar.getInstance()
    calendarSecond.time = dateSecond
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hourFirst = calendar.get(Calendar.HOUR_OF_DAY)
    val hourSecond = calendarSecond.get(Calendar.HOUR_OF_DAY)
    val minutesFirst = calendar.get(Calendar.MINUTE)
    val minutesSecond = calendarSecond.get(Calendar.MINUTE)
    return listOf(month, day, hourFirst, hourSecond, minutesFirst, minutesSecond)
}


fun NoteModel.mapNoteModelToLocal(): NoteLocalModel {
//    val dateFormat = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
//
//    val formattedMonth = month.toString().padStart(2, '0')
//    val formattedDay = day.toString().padStart(2, '0')
//    val formattedTimeStart = timeStart.toString().padStart(2, '0')
//    val formattedMinutesStart = minutesStart.toString().padStart(2, '0')
//    val formattedTimeFinish = timeFinish.toString().padStart(2, '0')
//    val formattedMinutesFinish = minutesFinish.toString().padStart(2, '0')
//
//    val dateStart = dateFormat.parse("$formattedMonth-$formattedDay $formattedTimeStart:$formattedMinutesStart:00")
//    val dateFinish = dateFormat.parse("$formattedMonth-$formattedDay $formattedTimeFinish:$formattedMinutesFinish:00")

    return NoteLocalModel(
        id = id,
        name = name,
        description = description,
        month = month.substring(0, 1) + month.substring(1).lowercase(Locale.ROOT),
        day = day,
        timeStart = timeStart,
        timeFinish = timeFinish,
        minutesStart = minutesStart,
        minutesFinish = minutesFinish
    )
}