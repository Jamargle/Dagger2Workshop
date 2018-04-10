package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenter
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenterImpl
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenterImpl

object PresenterFactory {

    private val addNoteFragmentPresenterInstance: AddNotePresenter by lazy { initAddNoteFragmentPresenter() }
    private val detailNoteFragmentPresenterInstance: DetailNoteFragmentPresenter by lazy { initDetailNoteFragmentPresenter() }

    fun createAddNotePresenter(): AddNotePresenter = addNoteFragmentPresenterInstance

    fun createDetailNotePresenter(): DetailNoteFragmentPresenter = detailNoteFragmentPresenterInstance

    private fun initAddNoteFragmentPresenter(): AddNotePresenter = AddNotePresenterImpl(instantiateNotesRepository())

    private fun initDetailNoteFragmentPresenter(): DetailNoteFragmentPresenter = DetailNoteFragmentPresenterImpl(instantiateNotesRepository())

    private fun instantiateNotesRepository() = RepositoryFactory.createNotesRepository()

}
