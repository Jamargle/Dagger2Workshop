package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl

object NetworkApiFactory {

    private val notesServiceApiInstance: NotesServiceApi by lazy { initNotesServiceApi() }

    fun createNetworkApi(): NotesServiceApi = notesServiceApiInstance

    private fun initNotesServiceApi(): NotesServiceApi = NotesServiceApiImpl()

}
