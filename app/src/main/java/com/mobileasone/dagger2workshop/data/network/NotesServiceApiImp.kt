package com.mobileasone.dagger2workshop.data.network

import android.os.Handler
import com.mobileasone.dagger2workshop.domain.Note

class NotesServiceApiImpl
private constructor() : NotesServiceApi {

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1500L
        private val NOTES_SERVICE_DATA: MutableMap<String, Note> = FakeNotesServiceApiEndpoint.loadPersistedNotes()

        private var INSTANCE: NotesServiceApiImpl? = null

        fun getInstance(): NotesServiceApi {
            if (INSTANCE == null) {
                INSTANCE = NotesServiceApiImpl()
            }
            return INSTANCE as NotesServiceApi
        }
    }

    override fun getAllNotes(callback: NotesServiceApi.NotesServiceCallback<List<Note>>) {
        // Simulate network by delaying the execution.
        val handler = Handler()
        handler.postDelayed({
            val notes = ArrayList(NOTES_SERVICE_DATA.values)
            callback.onLoaded(notes)
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun getNote(
            noteId: String,
            callback: NotesServiceApi.NotesServiceCallback<Note>) {

        val note = NOTES_SERVICE_DATA[noteId]
        note?.let {
            // Simulate network by delaying the execution.
            val handler = Handler()
            handler.postDelayed({
                callback.onLoaded(it)
            }, SERVICE_LATENCY_IN_MILLIS)
        }
    }

    override fun saveNote(note: Note) {
        NOTES_SERVICE_DATA[note.getId()] = note
    }

}
