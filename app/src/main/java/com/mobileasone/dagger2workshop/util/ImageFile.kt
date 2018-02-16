package com.mobileasone.dagger2workshop.util

interface ImageFile {

    fun create(
            name: String,
            extension: String)

    fun exists(): Boolean

    fun delete()

    fun getPath(): String?

}
