package com.mobileasone.dagger2workshop.di

import android.app.Application
import com.mobileasone.dagger2workshop.Dagger2WorkshopApp
import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Each component-annotated class will be used by Dagger to generate code to provide dependencies
 * tied to these provide methods below (the ones with @Provides annotation or @Binds annotation)
 */
@Singleton
@Component(modules = [
    (SampleAppModule::class),
    (ActivityBuilder::class),
    (FragmentBuilder::class)])
interface SampleAppComponent : AndroidInjector<Dagger2WorkshopApp> {

    fun inject(app: Application)

    fun provideNotesServiceApi(): NotesServiceApi

    fun provideNoteListRepository(): NotesLocalMemoryRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SampleAppComponent

    }

}
