package com.mobileasone.dagger2workshop.presentation.addnotes


interface AddNotePresenter {

    fun attachView(view: View)

    fun detachView()

    fun getView(): AddNotePresenter.View?

    fun saveNote(
            title: String,
            description: String,
            imagePath: String?)

    fun takePicture()

    fun imageAvailable()

    fun imageCaptureFailed()

    interface View {

        fun showEmptyNoteError()

        fun showNotesList()

        fun openCamera(saveTo: String)

        fun showImagePreview(imagePath: String)

        fun showImageError()
    }

}
