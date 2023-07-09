package com.example.simbirsoft.data.local.datasource

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.simbirsoft.data.local.database.AppDataBase
import com.example.simbirsoft.data.local.model.NoteLocalModel

class NoteDataSource(context: Context) {

    val db by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val noteDao by lazy {
        db.getNoteDao()
    }

    companion object {
        private const val DATABASE_NAME = "notesApp.db"
    }

    suspend fun insertNote(note: NoteLocalModel) {
        noteDao.insert(note)
    }

    suspend fun deleteNote(note: NoteLocalModel) {
        noteDao.delete(note)
    }

    suspend fun getNotesByDate(month: String, day:String): List<NoteLocalModel>? {
        Log.e("datasource", noteDao.getNotesByDate1(month, day).toString())
        Log.e(day, month)
        return noteDao.getNotesByDate1(month, day)
    }

    suspend fun insetAllNotes(notes: List<NoteLocalModel>) {
        noteDao.insertAll(notes)
    }

    suspend fun getNoteById(id: Int): NoteLocalModel {
        return noteDao.getNoteById(id)
    }
}