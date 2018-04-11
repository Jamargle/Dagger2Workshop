package com.mobileasone.dagger2workshop.di

import dagger.Subcomponent

@Subcomponent(modules = [(OtherFragmentBModule::class)])
interface OtherFragmentBComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): OtherFragmentBComponent
    }

}
