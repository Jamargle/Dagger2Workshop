package com.mobileasone.dagger2workshop.presentation.notedetail

interface DetailNoteFragmentPresenter {

    fun attachView(view: View)

    fun detachView()

    fun getView(): DetailNoteFragmentPresenter.View?

    fun openNote(noteId: String?)

    interface View {

        fun setProgressIndicator(active: Boolean)

        fun showMissingNote()

        fun hideTitle()

        fun showTitle(title: String)

        fun showImage(imageUrl: String)

        fun hideImage()

        fun hideDescription()

        fun showDescription(description: String)

    }

}
