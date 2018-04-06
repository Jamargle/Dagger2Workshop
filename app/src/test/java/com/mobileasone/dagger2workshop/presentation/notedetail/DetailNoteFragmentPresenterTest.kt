package com.mobileasone.dagger2workshop.presentation.notedetail

import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
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
 * Unit tests for the implementation of [DetailNoteFragmentPresenter]
 */
class DetailNoteFragmentPresenterTest {

    @Mock private lateinit var notesRepository: NotesRepository
    @Mock private lateinit var view: DetailNoteFragmentPresenter.View

    private lateinit var presenter: DetailNoteFragmentPresenter

    @Before
    fun setupNotesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        presenter = DetailNoteFragmentPresenterImpl(notesRepository)
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
    fun openNote_showMissingNoteWhenNoteIdIsNull() {
        // When
        presenter.attachView(view)
        presenter.openNote(null)

        // Then
        verify(view).showMissingNote()
    }

    @Test
    fun openNote_showMissingNoteWhenNoteIdIsEmpty() {
        // When
        presenter.attachView(view)
        presenter.openNote("")

        // Then
        verify(view).showMissingNote()
    }

    @Test
    fun openNote_getsNoteFromRepositoryAndLoadIntoView() {
        // Given
        val note = Note(TITLE_TEST, DESCRIPTION_TEST, IMAGE_PATH_TEST)
        val noteCallbackCaptor = argumentCaptor<NotesRepository.GetNoteCallback>()
        `when`(notesRepository.getNote(eq(note.getId()), noteCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    noteCallbackCaptor.firstValue.onNoteLoaded(note)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.openNote(note.getId())

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNote(note.getId(), noteCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showTitle(TITLE_TEST)
        verify(view).showDescription(DESCRIPTION_TEST)
        verify(view).showImage(IMAGE_PATH_TEST)
    }

    @Test
    fun openNote_getsNoteFromRepositoryAndLoadIntoViewWithoutTitle() {
        // Given
        val note = Note(null, DESCRIPTION_TEST, IMAGE_PATH_TEST)
        val noteCallbackCaptor = argumentCaptor<NotesRepository.GetNoteCallback>()
        `when`(notesRepository.getNote(eq(note.getId()), noteCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    noteCallbackCaptor.firstValue.onNoteLoaded(note)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.openNote(note.getId())

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNote(note.getId(), noteCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).hideTitle()
        verify(view).showDescription(DESCRIPTION_TEST)
        verify(view).showImage(IMAGE_PATH_TEST)
    }

    @Test
    fun openNote_getsNoteFromRepositoryAndLoadIntoViewWithoutDescription() {
        // Given
        val note = Note(TITLE_TEST, null, IMAGE_PATH_TEST)
        val noteCallbackCaptor = argumentCaptor<NotesRepository.GetNoteCallback>()
        `when`(notesRepository.getNote(eq(note.getId()), noteCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    noteCallbackCaptor.firstValue.onNoteLoaded(note)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.openNote(note.getId())

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNote(note.getId(), noteCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showTitle(TITLE_TEST)
        verify(view).hideDescription()
        verify(view).showImage(IMAGE_PATH_TEST)
    }

    @Test
    fun openNote_getsNoteFromRepositoryAndLoadIntoViewWithoutImage() {
        // Given
        val note = Note(TITLE_TEST, DESCRIPTION_TEST)
        val noteCallbackCaptor = argumentCaptor<NotesRepository.GetNoteCallback>()
        `when`(notesRepository.getNote(eq(note.getId()), noteCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    noteCallbackCaptor.firstValue.onNoteLoaded(note)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.openNote(note.getId())

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNote(note.getId(), noteCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showTitle(TITLE_TEST)
        verify(view).showDescription(DESCRIPTION_TEST)
        verify(view).hideImage()
    }

    @Test
    fun openNote_showMissingNoteWhenNoteIdIsUnknown() {
        // Given
        val noteCallbackCaptor = argumentCaptor<NotesRepository.GetNoteCallback>()
        `when`(notesRepository.getNote(eq(INVALID_ID), noteCallbackCaptor.capture()))
                .thenAnswer({ _ ->
                    noteCallbackCaptor.firstValue.onNoteLoaded(null)
                    null
                })

        // When
        presenter.attachView(view)
        presenter.openNote(INVALID_ID)

        // Then
        verify(view).setProgressIndicator(true)
        verify(notesRepository).getNote(INVALID_ID, noteCallbackCaptor.firstValue)
        verify(view).setProgressIndicator(false)
        verify(view).showMissingNote()
    }

    companion object {
        const val INVALID_ID = "INVALID_ID"
        const val TITLE_TEST = "title"
        const val DESCRIPTION_TEST = "description"
        const val IMAGE_PATH_TEST = "image path"
    }

}
