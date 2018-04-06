package com.mobileasone.dagger2workshop.data.local

import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
class NotesLocalMemoryRepositoryTest {

    companion object {
        private val NOTES = arrayListOf<Note>(
                Note("Title1", "Description1"),
                Note("Title2", "Description2"))
    }

    @Mock private lateinit var notesServiceApi: NotesServiceApi
    @Mock private lateinit var notesCallback: NotesRepository.LoadNotesCallback
    @Mock private lateinit var noteCallback: NotesRepository.GetNoteCallback

    private lateinit var notesRepository: NotesRepository

    //    /**
    //     * [ArgumentCaptor] is a powerful Mockito API to capture argument values and use them to
    //     * perform further actions or assertions on them.
    //     */
    //    @Captor
    //    private lateinit var notesServiceCallbackCaptor: ArgumentCaptor<NotesServiceApi.NotesServiceCallback<List<Note>>>
    //    /**
    //     * As we are working in Kotlin with null-safe stuff, we will use argumentCaptor from library mockito_kotlin in this way.
    //     * val captor = argumentCaptor<NotesServiceApi.NotesServiceCallback<List<Note>>>()
    //     */

    @Before
    fun setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        notesRepository = NotesLocalMemoryRepository(notesServiceApi)
    }

    @After
    fun cleanUp() {
        verifyNoMoreInteractions(notesServiceApi, noteCallback, notesCallback)
    }

    @Test
    fun getNotes_cacheWithData_returnsRepositoryCache() {
        // Given a setup Captor to capture callbacks
        val someCachedNotes = NOTES
        NotesLocalMemoryRepository.cachedNotes = someCachedNotes

        // when
        notesRepository.getNotes(notesCallback)

        // then
        verify(notesCallback).onNotesLoaded(someCachedNotes)
    }

    @Test
    fun getNotes_withoutCachedData_callsApiAndCacheData() {
        // given
        val notesServiceCallbackCaptor = argumentCaptor<NotesServiceApi.NotesServiceCallback<List<Note>>>()

        `when`(notesServiceApi.getAllNotes(notesServiceCallbackCaptor.capture()))
                .thenAnswer({ invocation ->
                    (invocation.arguments[0] as NotesServiceApi.NotesServiceCallback<List<Note>>).onLoaded(NOTES)
                    null
                })

        // when
        notesRepository.getNotes(notesCallback)

        // then
        assertEquals(NotesLocalMemoryRepository.cachedNotes, NOTES)
        verify(notesCallback).onNotesLoaded(NOTES)
    }

    @Test
    fun saveNote_savesNoteToServiceAPIAndInvalidatesCache() {
        // Given
        val noteTitle = "note title"
        val noteDescription = "Some Note Description"
        val newNote = Note(noteTitle, noteDescription)
        val noteCaptor = argumentCaptor<Note>()

        // When
        notesRepository.saveNote(newNote)

        // Then
        assertTrue(NotesLocalMemoryRepository.cachedNotes.isEmpty())
        verify(notesServiceApi).saveNote(noteCaptor.capture())
        assertTrue(noteCaptor.firstValue.title == noteTitle)
        assertTrue(noteCaptor.firstValue.description == noteDescription)
    }

    @Test
    fun getNote_requestsSingleNoteFromNotesServiceApi() {
        // Given
        val noteId = "some note ID"
        val notesCallbackCaptor = argumentCaptor<NotesServiceApi.NotesServiceCallback<Note>>()
        val expectedNote = Note(noteId, "any description")
        `when`(notesServiceApi.getNote(eq(noteId), notesCallbackCaptor.capture()))
                .thenAnswer({ invocation ->
                    (invocation.arguments[1] as NotesServiceApi.NotesServiceCallback<Note>).onLoaded(expectedNote)
                    null
                })

        // When a note is requested from the notes repository
        notesRepository.getNote(noteId, noteCallback)

        // Then
        verify(noteCallback).onNoteLoaded(expectedNote)
        verify(notesServiceApi).getNote(eq(noteId), notesCallbackCaptor.capture())
    }

}
