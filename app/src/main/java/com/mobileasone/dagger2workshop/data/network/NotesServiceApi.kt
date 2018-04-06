package com.mobileasone.dagger2workshop.data.network

import com.mobileasone.dagger2workshop.domain.Note

interface NotesServiceApi {

    fun getAllNotes(callback: NotesServiceCallback<List<Note>>)

    fun getNote(noteId: String, callback: NotesServiceCallback<Note>)

    fun saveNote(note: Note)

    interface NotesServiceCallback<T> {
        fun onLoaded(notes: T)
    }

}
