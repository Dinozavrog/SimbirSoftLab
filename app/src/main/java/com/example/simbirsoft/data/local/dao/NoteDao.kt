package com.example.simbirsoft.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.simbirsoft.data.local.model.NoteLocalModel

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: NoteLocalModel)

    @Insert
    suspend fun insertAll(notes: List<NoteLocalModel>)

    @Delete
    suspend fun delete(note: NoteLocalModel)

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): NoteLocalModel

    @Query("SELECT * FROM notes WHERE day = :day AND month = :month")
    suspend fun getNotesByDate1(day: String, month: String): List<NoteLocalModel>?


}