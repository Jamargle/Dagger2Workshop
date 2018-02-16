package com.mobileasone.dagger2workshop.data.local

import android.support.annotation.VisibleForTesting
import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository

/**
 * Concrete implementation to load notes from the local data source.
 */
class NotesLocalMemoryRepository
private constructor(private val notesServiceApi: NotesServiceApi) : NotesRepository {

    companion object {
        /**
         * This method has reduced visibility for testing and is only visible to tests in the same
         * package.
         */
        @VisibleForTesting
        var cachedNotes: List<Note> = emptyList()

        private var INSTANCE: NotesLocalMemoryRepository? = null

        fun getInstance(notesServiceApi: NotesServiceApi): NotesRepository {
            if (INSTANCE == null) {
                INSTANCE = NotesLocalMemoryRepository(notesServiceApi)
            }
            return INSTANCE as NotesRepository
        }

    }

    override fun getNotes(callback: NotesRepository.LoadNotesCallback) {
        //Load from API only if needed.
        if (cachedNotes.isNotEmpty()) {
            callback.onNotesLoaded(cachedNotes)
        } else {
            notesServiceApi.getAllNotes(object : NotesServiceApi.NotesServiceCallback<List<Note>> {

                override fun onLoaded(notes: List<Note>) {
                    cachedNotes = notes.toList()
                    callback.onNotesLoaded(cachedNotes)
                }

            })
        }
    }

    override fun saveNote(note: Note) {
        notesServiceApi.saveNote(note)
        refreshData()
    }

    override fun getNote(
            noteId: String,
            callback: NotesRepository.GetNoteCallback) {

        notesServiceApi.getNote(noteId, object : NotesServiceApi.NotesServiceCallback<Note> {

            override fun onLoaded(notes: Note) {
                callback.onNoteLoaded(notes)
            }

        })
    }

    override fun refreshData() {
        cachedNotes = emptyList()
    }

}
