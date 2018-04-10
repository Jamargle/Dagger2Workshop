package com.mobileasone.dagger2workshop.di

import dagger.Subcomponent

@Subcomponent(modules = [(NoteListFragmentModule::class)])
interface NoteListFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): NoteListFragmentComponent
    }

}
