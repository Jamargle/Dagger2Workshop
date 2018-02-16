package com.mobileasone.dagger2workshop.presentation.notelist

import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import java.lang.ref.WeakReference


class NoteListFragmentPresenterImpl
private constructor(private val notesRepository: NotesRepository) : NoteListFragmentPresenter {

    companion object {

        private var INSTANCE: NoteListFragmentPresenter? = null

        fun getInstance(notesRepository: NotesRepository): NoteListFragmentPresenter {
            if (INSTANCE == null) {
                INSTANCE = NoteListFragmentPresenterImpl(notesRepository)
            }
            return INSTANCE as NoteListFragmentPresenter
        }

    }

    private var viewReference: WeakReference<NoteListFragmentPresenter.View>? = null

    override fun attachView(view: NoteListFragmentPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

    override fun getView(): NoteListFragmentPresenter.View? =
            if (viewReference == null) {
                null
            } else {
                viewReference?.get()
            }

    override fun loadNotes(forceUpdate: Boolean) {
        getView()?.setProgressIndicator(true)
        if (forceUpdate) {
            notesRepository.refreshData()
        }

        notesRepository.getNotes(object : NotesRepository.LoadNotesCallback {

            override fun onNotesLoaded(notes: List<Note>) {
                getView()?.setProgressIndicator(false)
                getView()?.showNotes(notes)
            }

        })
    }

    override fun addNewNote() {
        getView()?.showAddNote()
    }

    override fun openNoteDetails(requestedNote: Note) {
        getView()?.showNoteDetailUi(requestedNote.getId())
    }

}
