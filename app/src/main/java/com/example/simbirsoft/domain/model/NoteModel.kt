package com.example.simbirsoft.domain.model

data class NoteModel(
    val id: Int,
    val name: String,
    val description: String,
    val month: String,
    val day: String,
    val timeStart: Int,
    val timeFinish: Int,
    val minutesStart: Int,
    val minutesFinish: Int
        )