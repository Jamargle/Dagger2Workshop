package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * SampleAppModule provides the way to instantiate all dependencies needed in the application
 * scope.
 */
@Module(subcomponents = [
    NoteListFragmentComponent::class,
    DetailNoteFragmentComponent::class,
    AddNoteFragmentComponent::class,
    OtherFragmentBComponent::class,
    OtherFragmentCComponent::class
])
abstract class SampleAppModule {

    @Binds
    @Singleton
    abstract fun notesServiceApi(service: NotesServiceApiImpl): NotesServiceApi

    @Binds
    @Singleton
    abstract fun notesRepository(repository: NotesLocalMemoryRepository): NotesRepository

}
