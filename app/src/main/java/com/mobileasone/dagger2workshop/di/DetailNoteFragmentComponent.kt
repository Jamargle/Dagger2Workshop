package com.mobileasone.dagger2workshop.di

import dagger.Subcomponent

@Subcomponent(modules = [DetailNoteFragmentModule::class])
interface DetailNoteFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): DetailNoteFragmentComponent
    }

}
