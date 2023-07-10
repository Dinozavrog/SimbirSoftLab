import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.usecase.InsertNoteUseCase
import com.example.simbirsoft.presentation.presenter.CreateNoteViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class CreateNoteViewModelTest1 {

    @Mock
    private lateinit var insertNoteUseCase: InsertNoteUseCase
    private lateinit var viewModel: CreateNoteViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        insertNoteUseCase = mockk()
        viewModel = CreateNoteViewModel(insertNoteUseCase)
    }

    @Test
    fun `onSaveNote emits Navigate action`() = runBlockingTest {
        val note = NoteModel(
            id = 0,
            name = "Test",
            description = "Sample note",
            month = "",
            day = "",
            timeStart = 0,
            timeFinish = 0,
            minutesStart = 0,
            minutesFinish = 0
        )

        coEvery { insertNoteUseCase(any()) } returns Unit

        viewModel.event(CreateNoteViewModel.CreateNoteEvent.onSaveNoteClick(note))

        advanceUntilIdle()

        viewModel.action.collect { action ->
            assertEquals(CreateNoteViewModel.CreateNoteAction.Navigate, action)
        }

        coVerify { insertNoteUseCase(note) }
    }
}
