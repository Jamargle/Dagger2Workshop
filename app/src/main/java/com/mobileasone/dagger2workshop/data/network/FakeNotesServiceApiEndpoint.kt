package com.mobileasone.dagger2workshop.data.network

import com.mobileasone.dagger2workshop.domain.Note

object FakeNotesServiceApiEndpoint {

    private var DATA: MutableMap<String, Note> = HashMap(2)

    fun loadPersistedNotes(): MutableMap<String, Note> {
        addNote("Oh yes!", "I demand trial by Unit testing", null)
        addNote("Espresso", "UI Testing for Android", null)
        return DATA
    }

    private fun addNote(
            title: String,
            description: String,
            imageUrl: String?) {

        val newNote = Note(title, description, imageUrl)
        DATA.put(newNote.getId(), newNote)
    }

}
