package com.example.simbirsoft.data.local.datasource

import android.util.Log
import com.example.simbirsoft.data.utils.mapNoteModelToLocal
import com.example.simbirsoft.data.utils.toNoteModel
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.repository.NoteRepository

class NoteRepositoryImpl(private val dataSource: NoteDataSource): NoteRepository {
    override suspend fun insertNote(note: NoteModel) {
        dataSource.insertNote(note.mapNoteModelToLocal())
    }

    override suspend fun getNotesByDate(month: String, day: String): List<NoteModel>? {
        val response = dataSource.getNotesByDate(month, day)
        Log.e("response", response.toString())
        val notes = mutableListOf<NoteModel>()
        if (response != null) {
            for (note in response) {
                notes.add(note.toNoteModel())
            }
        }
        return notes
    }

    override suspend fun deleteNote(note: NoteModel) {
        dataSource.deleteNote(note.mapNoteModelToLocal())
    }

    override suspend fun insertAllNotes(notes: List<NoteModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Int): NoteModel {
        return dataSource.getNoteById(id).toNoteModel()
    }
}

