package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenter
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenterImpl

object PresenterFactory {

    private val addNoteFragmentPresenterInstance: AddNotePresenter by lazy { initAddNoteFragmentPresenter() }

    fun createAddNotePresenter(): AddNotePresenter = addNoteFragmentPresenterInstance

    private fun initAddNoteFragmentPresenter(): AddNotePresenter = AddNotePresenterImpl(instantiateNotesRepository())

    private fun instantiateNotesRepository() = RepositoryFactory.createNotesRepository()

}
