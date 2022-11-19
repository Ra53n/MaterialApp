import com.example.materialapp.domain.data.CameraName
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.interactors.NotesInteractor
import com.example.materialapp.domain.mappers.NoteMapper
import com.example.materialapp.domain.repos.NasaRepositoryImpl
import com.example.materialapp.ui.view.utils.DateFormatter
import com.example.materialapp.ui.viewmodel.MainViewModel
import com.example.materialapp.ui.viewmodel.MarsViewModel
import com.example.materialapp.ui.viewmodel.NoteEditBottomSheetViewModel
import com.example.materialapp.ui.viewmodel.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class ViewModelTests {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var marsViewModel: MarsViewModel

    private lateinit var noteEditBottomSheetViewModel: NoteEditBottomSheetViewModel

    private lateinit var notesViewModel: NotesViewModel

    @Mock
    private lateinit var notesInteractor: NotesInteractor

    @Mock
    private lateinit var nasaRepository: NasaRepositoryImpl

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Mock
    private lateinit var noteMapper: NoteMapper

    private val dispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(nasaRepository, dateFormatter)
        marsViewModel = MarsViewModel(nasaRepository)
        notesViewModel = NotesViewModel(notesInteractor)
        noteEditBottomSheetViewModel = NoteEditBottomSheetViewModel(notesInteractor, noteMapper)
    }

    @Test
    fun requestPictureOfTheDay_Test() {
        runTest {
            val dateForRequest = "2022-05-11"
            `when`(dateFormatter.getDateForRequest(anyBoolean())).thenReturn(dateForRequest)
            mainViewModel.requestPictureOfTheDay(false)
            verify(nasaRepository).pictureOfTheDay(dateForRequest)
        }
    }

    @Test
    fun requestPictureOfTheDayRepo_TestException() {
        runTest {
            val dateForRequest = "2030-05-11"
            Assert.assertThrows(Exception::class.java) {
                runTest {
                    nasaRepository.pictureOfTheDay(dateForRequest)
                }
            }
        }
    }

    @Test
    fun requestMarsPhoto_Test() {
        runTest {
            marsViewModel.requestMarsPhotos(CameraName.Chemcam)
            verify(nasaRepository).marsRoverPhotos(CameraName.Chemcam.value)
        }
    }

    @Test
    fun requestMarsPhotoRepo_TestException() {
        runTest {
            Assert.assertThrows(Exception::class.java) {
                runTest {
                    nasaRepository.marsRoverPhotos(anyString())
                }
            }
        }
    }


    @Test
    fun updateNote_Test() {
        runTest {
            val someNoteEntity = NoteEntity("", "", "", "", 0)
            `when`(noteMapper.map(anyString(), anyString(), anyString(), anyString())).thenReturn(
                someNoteEntity
            )
            noteEditBottomSheetViewModel.onUpdateNote("", "", "", "1")
            verify(notesInteractor).updateNote(someNoteEntity)
        }
    }

    @Test
    fun requestNotes_Test() {
        runTest {
            notesViewModel.requestNotes()
            verify(notesInteractor).getAllNotes()
        }
    }

    @Test
    fun removeItem_Test() {
        runTest {
            val someNoteEntity = NoteEntity("", "", "", "", 0)
            notesViewModel.removeItem(someNoteEntity)
            verify(notesInteractor).deleteNote(someNoteEntity)
        }
    }

    @Test
    fun replaceItems_Test() {
        runTest {
            val firstNote = NoteEntity("1", "", "", "", 0)
            val secondNote = NoteEntity("2", "", "", "", 0)
            notesViewModel.replaceItems(firstNote, secondNote)
            verify(notesInteractor).swapNotes(firstNote, secondNote)
        }
    }
}