package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragmentPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * This module will provide dependencies for DetailNoteFragment
 */
@Module
class DetailNoteFragmentModule {

    @Provides
    fun noteListFragmentPresenter(presenter: DetailNoteFragmentPresenterImpl): DetailNoteFragmentPresenter = presenter

}
