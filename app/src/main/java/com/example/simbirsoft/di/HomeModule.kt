package com.example.simbirsoft.di

import com.example.simbirsoft.data.local.datasource.NoteDataSource
import com.example.simbirsoft.data.local.datasource.NoteRepositoryImpl
import com.example.simbirsoft.domain.repository.NoteRepository
import com.example.simbirsoft.domain.usecase.DeleteNoteUseCase
import com.example.simbirsoft.domain.usecase.GetNoteByIdUseCase
import com.example.simbirsoft.domain.usecase.GetNotesUseCase
import com.example.simbirsoft.domain.usecase.InsertNoteUseCase
import org.koin.dsl.module

val noteModule = module {
    single {
        provideNoteRepository(
            dataSource = get()
        )
    }
    single {
        provideGetNotesUseCase(
            noteRepository = get()
        )
    }
    single {
        provideGetNoteByIdUseCase(
            noteRepository = get()
        )
    }
    single {
        provideDeleteNoteUseCase(
            noteRepository = get()
        )
    }
    single {
        provideInsertNoteUseCase(
            noteRepository = get()
        )
    }
}

private fun provideNoteRepository(
    dataSource: NoteDataSource
): NoteRepository = NoteRepositoryImpl(dataSource)

private fun provideGetNoteByIdUseCase(
    noteRepository: NoteRepository
): GetNoteByIdUseCase = GetNoteByIdUseCase(noteRepository)

private fun provideGetNotesUseCase(
    noteRepository: NoteRepository
):GetNotesUseCase = GetNotesUseCase(noteRepository)

private fun provideDeleteNoteUseCase(
    noteRepository: NoteRepository
): DeleteNoteUseCase = DeleteNoteUseCase()

private fun provideInsertNoteUseCase(
    noteRepository: NoteRepository
): InsertNoteUseCase = InsertNoteUseCase(noteRepository)