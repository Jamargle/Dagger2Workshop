package com.mobileasone.dagger2workshop.di

import dagger.Subcomponent

@Subcomponent(modules = [(OtherFragmentCModule::class)])
interface OtherFragmentCComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): OtherFragmentCComponent
    }

}
