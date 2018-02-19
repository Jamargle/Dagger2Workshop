package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenter
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenterImpl
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenterImpl
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenterImpl

object PresenterFactory {

    fun createNoteListPresenter(): NoteListFragmentPresenter = NoteListFragmentPresenterImpl.getInstance(instantiateNotesRepository())

    fun createAddNotePresenter(): AddNotePresenter = AddNotePresenterImpl.getInstance(instantiateNotesRepository())

    fun createDetailNotePresenter(): DetailNoteFragmentPresenter = DetailNoteFragmentPresenterImpl.getInstance(instantiateNotesRepository())

    private fun instantiateNotesRepository() = RepositoryFactory.createNotesRepository()

}
