package com.mobileasone.dagger2workshop.di

import dagger.Subcomponent

@Subcomponent(modules = [AddNoteFragmentModule::class])
interface AddNoteFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): AddNoteFragmentComponent
    }

}
