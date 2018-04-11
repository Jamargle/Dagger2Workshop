package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenter
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNotePresenterImpl
import com.mobileasone.dagger2workshop.util.ImageFile
import com.mobileasone.dagger2workshop.util.ImageFileImpl
import dagger.Module
import dagger.Provides

/**
 * This module will provide dependencies for NoteListFragment
 */
@Module
class AddNoteFragmentModule {

    @Provides
    fun imageFile(): ImageFile {
        return ImageFileImpl()
    }

    @Provides
    fun addNoteFragmentPresenter(repository: NotesRepository, imageFile: ImageFile): AddNotePresenter {
        return AddNotePresenterImpl(repository, imageFile)
    }

}
