package com.example.simbirsoft.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "notes")
data class NoteLocalModel (
    @PrimaryKey(autoGenerate = true)
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