package com.example.simbirsoft.di

import android.content.Context
import androidx.room.Room
import com.example.simbirsoft.data.local.dao.NoteDao
import com.example.simbirsoft.data.local.database.AppDataBase
import com.example.simbirsoft.data.local.datasource.NoteDataSource
import org.koin.dsl.module

const val DATABASE_NAME = "simbirsoft.db"

val dataBaseModule = module {

    single {
        provideAppDataBase(
            get()
        )
    }
    single {
        provideNoteDao(
            get()
        )
    }
    single {
        provideNoteDataSource(
            get()
        )
    }
}

fun provideNoteDataSource(context: Context): NoteDataSource {
    return NoteDataSource(context)
}

private fun provideAppDataBase(
    context: Context
): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
    .build()

private fun provideNoteDao(
    database: AppDataBase
): NoteDao = database.getNoteDao()

