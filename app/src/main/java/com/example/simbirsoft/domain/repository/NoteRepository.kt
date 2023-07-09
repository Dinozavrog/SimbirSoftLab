package com.example.simbirsoft.domain.repository

import com.example.simbirsoft.domain.model.NoteModel

interface NoteRepository {

    suspend fun insertNote(note: NoteModel)

    suspend fun getNotesByDate(month: String, day:String): List<NoteModel>?

    suspend fun deleteNote(note: NoteModel)

    suspend fun insertAllNotes(notes: List<NoteModel>)

    suspend fun getNoteById(id: Int): NoteModel
}