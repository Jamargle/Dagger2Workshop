package com.mobileasone.dagger2workshop.domain

import java.util.*

data class Note constructor(
        val title: String?,
        val description: String?,
        val imageUrl: String? = null) {

    private val noteId: String = UUID.randomUUID().toString()

    fun getId() = noteId

    fun isEmpty() = (title.isNullOrBlank() && description.isNullOrBlank())

}
