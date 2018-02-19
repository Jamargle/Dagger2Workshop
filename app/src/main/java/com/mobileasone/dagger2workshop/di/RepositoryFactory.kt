package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository

object RepositoryFactory {

    fun createNotesRepository(): NotesRepository = NotesLocalMemoryRepository.getInstance(instantiateNetworkApi())

    private fun instantiateNetworkApi() = NetworkApiFactory.createNetworkApi()

}
