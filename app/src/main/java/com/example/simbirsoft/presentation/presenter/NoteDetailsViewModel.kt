package com.example.simbirsoft.presentation.presenter

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.usecase.GetNoteByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class NoteDetailsViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DetailsViewState())
    val state: StateFlow<DetailsViewState>
        get() = _state.asStateFlow()

    fun event(detailEvent: DetailsEvent) {
        when(detailEvent) {
            is DetailsEvent.OnLoadNote -> onLoadNote(detailEvent.noteId)
        }
    }

    private fun onLoadNote(noteId: Int) {
        viewModelScope.launch {
            try {
                _state.emit(
                    _state.value.copy(
                        loading = true
                    )
                )
                _state.emit(
                    _state.value.copy(
                        note = getNoteByIdUseCase(noteId)
                    )
                )
            } catch (error: Throwable) {
                Timber.e(error.toString())
            } finally {
                _state.emit(
                    _state.value.copy(
                        loading = false
                    )
                )
            }
        }
    }

    @Immutable
    data class DetailsViewState(
        val loading: Boolean = false,
        val note: NoteModel? = null,
    )

    sealed interface DetailsEvent {
        data class OnLoadNote(val noteId: Int) : DetailsEvent
    }
}