package com.mobileasone.dagger2workshop.domain.repositories

import com.mobileasone.dagger2workshop.domain.Note

interface NotesRepository {

    fun getNotes(callback: LoadNotesCallback)

    fun getNote(noteId: String, callback: GetNoteCallback)

    fun saveNote(note: Note)

    fun refreshData()

    interface LoadNotesCallback {
        fun onNotesLoaded(notes: List<Note>)
    }

    interface GetNoteCallback {
        fun onNoteLoaded(note: Note?)
    }

}
