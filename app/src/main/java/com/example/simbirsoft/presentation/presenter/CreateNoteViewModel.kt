package com.example.simbirsoft.presentation.presenter

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CreateNoteViewState())
    val state: StateFlow<CreateNoteViewState>
        get() = _state.asStateFlow()
    
    private val _action = MutableSharedFlow<CreateNoteAction?>()
    val action: SharedFlow<CreateNoteAction?>
        get() = _action.asSharedFlow()
    
    fun event(createNoteEvent: CreateNoteEvent) {
        when (createNoteEvent) {
            is CreateNoteEvent.onSaveNoteClick -> onSaveNote(createNoteEvent.note)
        }
    }

    private fun onSaveNote(note: NoteModel) {
        viewModelScope.launch {
            try {
                insertNoteUseCase(note = note)
                _action.emit(
                    CreateNoteAction.Navigate
                )
            } catch (error: Throwable) {
                Timber.e(error.toString())
            }
        }
    }


    @Immutable
    data class CreateNoteViewState(
        val note: NoteModel? = null
    )

    sealed interface CreateNoteAction {
        object Navigate: CreateNoteAction
    }

    sealed interface CreateNoteEvent {
        data class onSaveNoteClick(val note: NoteModel): CreateNoteEvent
    }
}