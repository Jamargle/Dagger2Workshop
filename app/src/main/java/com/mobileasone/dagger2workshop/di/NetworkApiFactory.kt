package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.data.network.NotesServiceApi
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl

object NetworkApiFactory {

    fun createNetworkApi(): NotesServiceApi {
        return NotesServiceApiImpl.getInstance()
    }

}
