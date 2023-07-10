package com.example.simbirsoft.data.local.datasource

import com.example.simbirsoft.data.remote.addNotesFromJson
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
        return response.map { it.toNoteModel() }
    }

    override suspend fun deleteNote(note: NoteModel) {
        dataSource.deleteNote(note.mapNoteModelToLocal())
    }

    override suspend fun insertAllNotes() {
        val notes = addNotesFromJson()
        dataSource.insetAllNotes(notes)
    }

    override suspend fun getNoteById(id: Int): NoteModel {
        return dataSource.getNoteById(id).toNoteModel()
    }
}

