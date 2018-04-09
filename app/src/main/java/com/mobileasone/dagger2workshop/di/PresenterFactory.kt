package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenter
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenterImpl
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenterImpl
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenterImpl

object PresenterFactory {

    private val noteListFragmentPresenterInstance: NoteListFragmentPresenter by lazy { initNoteListFragmentPresenter() }
    private val addNoteFragmentPresenterInstance: AddNotePresenter by lazy { initAddNoteFragmentPresenter() }
    private val detailNoteFragmentPresenterInstance: DetailNoteFragmentPresenter by lazy { initDetailNoteFragmentPresenter() }

    fun createNoteListPresenter(): NoteListFragmentPresenter = noteListFragmentPresenterInstance

    fun createAddNotePresenter(): AddNotePresenter = addNoteFragmentPresenterInstance

    fun createDetailNotePresenter(): DetailNoteFragmentPresenter = detailNoteFragmentPresenterInstance

    private fun initNoteListFragmentPresenter(): NoteListFragmentPresenter = NoteListFragmentPresenterImpl(instantiateNotesRepository())

    private fun initAddNoteFragmentPresenter(): AddNotePresenter = AddNotePresenterImpl(instantiateNotesRepository())

    private fun initDetailNoteFragmentPresenter(): DetailNoteFragmentPresenter = DetailNoteFragmentPresenterImpl(instantiateNotesRepository())

    private fun instantiateNotesRepository() = RepositoryFactory.createNotesRepository()

}
