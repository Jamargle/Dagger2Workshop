package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository

object RepositoryFactory {

    private val notesLocalMemoryRepositoryInstance: NotesRepository by lazy { initNotesRepository() }

    fun createNotesRepository(): NotesRepository = notesLocalMemoryRepositoryInstance

    private fun initNotesRepository(): NotesRepository = NotesLocalMemoryRepository(instantiateNetworkApi())

    private fun instantiateNetworkApi() = NetworkApiFactory.createNetworkApi()

}
