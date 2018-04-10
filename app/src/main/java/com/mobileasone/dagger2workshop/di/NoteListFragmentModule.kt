package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenter
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragmentPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * This module will provide dependencies for NoteListFragment
 */
@Module
class NoteListFragmentModule {

    @Provides
    fun noteListFragmentPresenter(repository: NotesRepository): NoteListFragmentPresenter {
        return NoteListFragmentPresenterImpl(repository)
    }

}
