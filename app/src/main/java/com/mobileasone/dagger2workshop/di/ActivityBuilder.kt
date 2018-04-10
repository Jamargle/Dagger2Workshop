package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.notelist.NoteListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindNoteListActivity(): NoteListActivity

}
