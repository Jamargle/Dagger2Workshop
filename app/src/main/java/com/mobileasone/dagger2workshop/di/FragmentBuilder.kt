package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    // Add bindings for other sub-components here
    @ContributesAndroidInjector(modules = [NoteListFragmentModule::class])
    abstract fun bindNoteListFragment(): NoteListFragment

}
