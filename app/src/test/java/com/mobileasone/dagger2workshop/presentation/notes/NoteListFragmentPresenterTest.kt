package com.mobileasone.dagger2workshop.presentation.notes

import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenterImpl
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the implementation of [NoteListFragmentPresenter]
 */
class NoteListFragmentPresenterTest {

    @Mock private lateinit var notesRepository: NotesRepository
    @Mock private lateinit var view: NoteListFragmentPresenter.View

    private lateinit var presenter: NoteListFragmentPresenter

    @Before
    fun setupNotesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        presenter = NoteListFragmentPresenterImpl(notesRepository)
    }

    @After
    fun cleanUp() {
        verifyNoMoreInteractions(view, notesRepository)
    }

    @Test
    fun attachView_createsWeakReferenceWithView() {
        // When
        presenter.attachView(view)

        // Then
        assertTrue(presenter.getView() != null)
    }

    @Test
    fun detachView_removesReferenceToView() {
        // When
        presenter.detachView()

        // Then
        assertTrue(presenter.getView() == null)
    }

    @Test
    fun loadNotes_loadsNotesIntoViewWithFullRefreshingWhenForceUpdateTrue() {
        // Given
        val expectedNotes = listOf(
                Note(TITLE_TEST, DESCRIPTION_TEST, IMAGE_PATH_TEST),
                Note(TITLE_TEST, DESCRIPTION_TEST, IMAGE_PATH_TEST))
        val notesCallbackCaptor = argumentCaptor<NotesRepository.LoadNotesCallback>()
        `when`(notesRepository.getNotes(notesCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    notesCallbackCaptor.firstValue.onNotesLoaded(expectedNotes)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.loadNotes(true)

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).refreshData()
        verify(notesRepository).getNotes(notesCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showNotes(expectedNotes)
    }

    @Test
    fun loadNotes_loadsNotesIntoViewWithoutRefreshingWhenForceUpdateFalse() {
        // Given
        val expectedNotes = listOf(
                Note(TITLE_TEST, DESCRIPTION_TEST, IMAGE_PATH_TEST),
                Note(TITLE_TEST, DESCRIPTION_TEST, IMAGE_PATH_TEST))
        val notesCallbackCaptor = argumentCaptor<NotesRepository.LoadNotesCallback>()
        `when`(notesRepository.getNotes(notesCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    notesCallbackCaptor.firstValue.onNotesLoaded(expectedNotes)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.loadNotes(false)

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNotes(notesCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showNotes(expectedNotes)
    }

    @Test
    fun addNewNote_showsAddNote() {
        // When
        presenter.attachView(view)
        presenter.addNewNote()

        // Then
        verify(view).showAddNote()
    }

    @Test
    fun openNoteDetails_showsNoteDetailUi() {
        // Given
        val note = Note(TITLE_TEST, DESCRIPTION_TEST)
        // When
        presenter.attachView(view)
        presenter.openNoteDetails(note)

        // Then
        verify(view).showNoteDetailUi(note.getId())
    }

    companion object {
        const val TITLE_TEST = "title"
        const val DESCRIPTION_TEST = "description"
        const val IMAGE_PATH_TEST = "image path"
    }

}
