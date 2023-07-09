package com.example.simbirsoft.domain.usecase

import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteByIdUseCase (
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): NoteModel = withContext(Dispatchers.IO) {
        noteRepository.getNoteById(id)
    }
}