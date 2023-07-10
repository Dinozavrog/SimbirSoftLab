package com.example.simbirsoft.presentation.presenter

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.usecase.GetNoteByIdUseCase
import com.example.simbirsoft.domain.usecase.GetNotesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val getNotesUseCase: GetNotesUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<HomeAction?>()
    val action: SharedFlow<HomeAction?>
        get() = _action.asSharedFlow()

    fun event(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.OnNewMonthClick -> onGetNewMonth()
            is HomeEvent.OnDayClick -> onLoadNotes(homeEvent.month, homeEvent.day)
            is HomeEvent.OnNoteClick -> onNoteClick(homeEvent.note)
            is HomeEvent.OnCreateNoteClick -> onCreateNoteClick()
        }
    }

    private fun onLoadNotes(month: String, day:String) {
        viewModelScope.launch {
            try {
                _state.emit(
                    _state.value.copy(
                        noteList = getNotesUseCase(month, day)
                    )
                )
            } catch (error: Throwable) {
                Timber.e(error.toString())
            }
        }
    }

    private fun onGetNewMonth() {
        TODO("Not yet implemented")
    }

    private fun onNoteClick(note: NoteModel) {
        viewModelScope.launch {
            _action.emit(
                HomeAction.Navigate(noteId = note.id ?: 0)
            )
        }
    }

    private fun onCreateNoteClick() {
        viewModelScope.launch {
            _action.emit(
                HomeAction.NavigateToAddScreen
            )
        }
    }

    @Immutable
    data class HomeViewState(
        val noteList: List<NoteModel>? = null,
        val note: NoteModel? = null,
    )

    sealed interface HomeAction {
        data class Navigate(val noteId: Int): HomeAction
        object NavigateToAddScreen: HomeAction
    }

    sealed interface HomeEvent {
        data class OnDayClick(val month: String, val day: String): HomeEvent
        data class OnNewMonthClick(val prevMonth: String): HomeEvent
        data class OnNoteClick(val note: NoteModel): HomeEvent
        object OnCreateNoteClick: HomeEvent
    }
}