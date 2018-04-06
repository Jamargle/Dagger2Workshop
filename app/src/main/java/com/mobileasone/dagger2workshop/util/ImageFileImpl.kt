package com.mobileasone.dagger2workshop.util

import android.os.Environment
import android.support.annotation.VisibleForTesting
import java.io.File

class ImageFileImpl : ImageFile {

    @VisibleForTesting
    var imageFile: File? = null

    override fun create(name: String, extension: String) {
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        imageFile = File.createTempFile(
                name, /* prefix */
                extension, /* suffix */
                storageDir) /* directory */
    }

    override fun exists() = null != imageFile
            && imageFile?.exists() ?: false

    override fun delete() {
        imageFile = null
    }

    override fun getPath(): String? = imageFile?.absolutePath

}
