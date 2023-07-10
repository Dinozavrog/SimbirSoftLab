package com.example.simbirsoft.domain.usecase

import com.example.simbirsoft.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNotesFromJsonUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        noteRepository.insertAllNotes()
    }
}