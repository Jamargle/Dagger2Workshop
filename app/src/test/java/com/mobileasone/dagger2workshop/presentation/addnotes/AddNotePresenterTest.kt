package com.mobileasone.dagger2workshop.presentation.addnotes

import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.util.ImageFile
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the implementation of [AddNotePresenter].
 */
class AddNotePresenterTest {

    @Mock private lateinit var notesRepository: NotesRepository
    @Mock private lateinit var imageFile: ImageFile
    @Mock private lateinit var view: AddNotePresenter.View

    private lateinit var presenter: AddNotePresenter

    @Before
    fun setupAddNotePresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        presenter = AddNotePresenterImpl.getInstance(notesRepository)
        presenter.attachView(view)
    }

    @After
    fun cleanUp() {
        verifyNoMoreInteractions(view, notesRepository, imageFile)
    }

    @Test
    fun attachView_viewAttached() {
        // When
        presenter.attachView(view)
        // Then
        Assert.assertTrue(presenter.getView() != null)
    }

    @Test
    fun detachView_viewDetached() {
        // Given
        presenter.attachView(view)
        // When
        presenter.detachView()
        // Then
        Assert.assertTrue(presenter.getView() == null)
    }

    @Test
    fun getView_WithoutViewAttached_returnsNull() {
        // Given
        presenter.detachView()
        // When
        val expectedView = presenter.getView()
        // Then
        Assert.assertTrue(expectedView == null)
    }

    @Test
    fun getView_WithViewAttached_returnsTheView() {
        // Given
        presenter.attachView(view)
        // When
        val expectedView = presenter.getView()
        // Then
        Assert.assertTrue(expectedView == view)
    }

    @Test
    fun saveNote_emptyNoteShowsErrorMessage() {
        // Given
        val title = " "
        val description = " "
        val path = "some path"

        // When
        presenter.saveNote(title, description, path)

        // Then
        verify(view).showEmptyNoteError()
    }

    @Test
    fun saveNote_noteOk_saveNoteInRepositoryAndShowsItInTheList() {
        // Given
        val title = "some title"
        val description = "some description"
        val path = "some path"

        // When
        presenter.saveNote(title, description, path)

        // Then
        val noteCaptor = argumentCaptor<Note>()
        verify(notesRepository).saveNote(noteCaptor.capture())
        verify(view).showNotesList()
        Assert.assertEquals(title, noteCaptor.firstValue.title)
        Assert.assertEquals(description, noteCaptor.firstValue.description)
        Assert.assertEquals(path, noteCaptor.firstValue.imageUrl)
    }

    @Ignore
    @Test
    fun takePicture_CreatesFileAndOpensCamera() {
        // TODO Add PowerMock to mock getExternalStoragePublicDirectory call
        //Given
        //        val expectedPath = "some path"
        //        val expectedFile = File(expectedPath)
        //        PowerMockito.mockStatic(Environment::class.java)
        //        PowerMockito.`when`(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)).thenReturn(expectedFile)

        // When
        presenter.takePicture()

        // Then
        //        verify(imageFile).create(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        //        verify(imageFile).getPath()
        //        val imageFilePathCaptor = argumentCaptor<String>()
        //        verify(view).openCamera(imageFilePathCaptor.capture())
        //        Assert.assertEquals(expectedPath, imageFilePathCaptor.lastValue)
    }

    @Test
    fun imageAvailable_SavesImageAndUpdatesUiWithThumbnail() {
        // Given
        val imageUrl = "path/to/file"
        imageFile.create(imageUrl, ".png")
        (presenter as AddNotePresenterImpl).imageFile = imageFile
        `when`(imageFile.exists()).thenReturn(true)
        `when`(imageFile.getPath()).thenReturn(imageUrl)

        // When
        presenter.imageAvailable()

        // Then
        verify(imageFile).create(imageUrl, ".png") // Initialization above
        verify(imageFile).exists()
        verify(imageFile).getPath()
        verify(view).showImagePreview(ArgumentMatchers.contains(imageUrl))
    }

    @Test
    fun imageAvailable_FileDoesNotExistShowsErrorUi() {
        // Given
        `when`(imageFile.exists()).thenReturn(false)
        (presenter as AddNotePresenterImpl).imageFile = imageFile

        // When
        presenter.imageAvailable()

        // Then
        verify(imageFile).exists()
        verify(view).showImageError()
        verify(imageFile).delete()
    }

    @Test
    fun noImageAvailable_ShowsErrorUi() {
        // Given
        (presenter as AddNotePresenterImpl).imageFile = imageFile

        // When
        presenter.imageCaptureFailed()

        // Then an error is shown in the UI and the image file is deleted
        verify(view).showImageError()
        verify(imageFile).delete()
    }

}
