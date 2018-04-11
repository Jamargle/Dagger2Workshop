package com.mobileasone.dagger2workshop.presentation.addnotes

import android.support.annotation.VisibleForTesting
import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.util.GeneralConstants
import com.mobileasone.dagger2workshop.util.ImageFile
import com.mobileasone.dagger2workshop.util.ImageFileImpl
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNotePresenterImpl
constructor(private val notesRepository: NotesRepository) : AddNotePresenter {

    private var viewReference: WeakReference<AddNotePresenter.View>? = null
    @VisibleForTesting lateinit var imageFile: ImageFile

    override fun attachView(view: AddNotePresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

    override fun getView(): AddNotePresenter.View? =
            if (viewReference == null) {
                null
            } else {
                viewReference?.get()
            }

    override fun saveNote(
            title: String,
            description: String,
            imagePath: String?) {

        val newNote = Note(title, description, imagePath)
        if (newNote.isEmpty()) {
            getView()?.showEmptyNoteError()
        } else {
            notesRepository.saveNote(newNote)
            getView()?.showNotesList()
        }
    }

    override fun takePicture() {
        val timeStamp = SimpleDateFormat(GeneralConstants.DATE_FORMAT, Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        instantiateImageFile(imageFileName)
        imageFile.getPath()?.let { getView()?.openCamera(it) }
    }

    override fun imageAvailable() {
        if (imageFile.exists()) {
            imageFile.getPath()?.let { getView()?.showImagePreview(it) }
        } else {
            imageCaptureFailed()
        }
    }

    override fun imageCaptureFailed() {
        imageFile.delete()
        getView()?.showImageError()
    }

    private fun instantiateImageFile(fileName: String) {
        imageFile = ImageFileImpl()
        imageFile.create(fileName, ".jpg")
    }

}
