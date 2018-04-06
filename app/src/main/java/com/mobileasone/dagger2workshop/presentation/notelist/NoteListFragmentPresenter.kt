package com.mobileasone.dagger2workshop.presentation.notelist

import com.mobileasone.dagger2workshop.domain.Note

interface NoteListFragmentPresenter {

    fun attachView(view: View)

    fun detachView()

    fun getView(): View?

    fun loadNotes(forceUpdate: Boolean)

    fun addNewNote()

    fun openNoteDetails(requestedNote: Note)

    interface View {

        fun setProgressIndicator(active: Boolean)

        fun showNotes(notes: List<Note>)

        fun showAddNote()

        fun showNoteDetailUi(noteId: String)
    }

}
