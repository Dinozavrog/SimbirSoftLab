import com.example.simbirsoft.di.noteModule
import com.example.simbirsoft.domain.model.NoteModel
import com.example.simbirsoft.domain.repository.NoteRepository
import com.example.simbirsoft.domain.usecase.GetNoteByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.assertFailsWith

class GetNoteByIdUseCaseTest : KoinTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            modules(noteModule)
        }
        noteRepository = mockk()
        getNoteByIdUseCase = GetNoteByIdUseCase(noteRepository)
    }

    @Test
    fun whenGetNoteByIdUseCaseSuccess() {
        val id = 1
        val expectedData: NoteModel = mockk()

        coEvery {
            noteRepository.getNoteById(id)
        } returns expectedData

        runTest {
            val result = getNoteByIdUseCase.invoke(id)

            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenGetNoteByIdUseCaseError() {
        val id = 1
        coEvery {
            noteRepository.getNoteById(id)
        } throws RuntimeException()

        runTest {
            assertFailsWith<RuntimeException> {
                getNoteByIdUseCase(id)
            }
        }
    }
}
