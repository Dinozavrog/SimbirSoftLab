package com.example.simbirsoft.domain.usecase

import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNotesUseCase (
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(month: String, day:String): List<NoteModel>? {
        return withContext(Dispatchers.IO) {
            noteRepository.getNotesByDate(month, day)
        }
    }
}