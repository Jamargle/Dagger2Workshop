package com.mobileasone.dagger2workshop.di

import com.example.mbasone.daggerworkshop.di.DetailNoteFragmentModule
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragment
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    // Add bindings for other sub-components here
    @ContributesAndroidInjector(modules = [NoteListFragmentModule::class])
    abstract fun bindNoteListFragment(): NoteListFragment

    @ContributesAndroidInjector(modules = [DetailNoteFragmentModule::class])
    abstract fun bindDetailNoteFragment(): DetailNoteFragment

}
