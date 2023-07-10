package com.example.simbirsoft.domain.usecase

import com.example.simbirsoft.di.noteModule
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.repository.NoteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.assertFailsWith

class GetNotesUseCaseTest: KoinTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var getNotesUseCase: GetNotesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            modules(noteModule)
        }
        noteRepository = mockk()
        getNotesUseCase = GetNotesUseCase(noteRepository)
    }

    @Test
    fun whenGetNotesUseCaseSuccess() {
        val month = ""
        val day = ""
        val expectedData: List<NoteModel> = mockk()

        coEvery {
            noteRepository.getNotesByDate(month = month, day = day)
        } returns expectedData

        runTest {
            val result = getNotesUseCase.invoke(month, day)

            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenGetNotesUseCaseError() {
        val month = ""
        val day = ""
        coEvery {
            noteRepository.getNotesByDate(month, day)
        } throws RuntimeException()

        runTest {
            assertFailsWith<RuntimeException> {
                getNotesUseCase(month, day)
            }
        }
    }
}