package com.mobileasone.dagger2workshop.presentation.notedetail

import android.support.annotation.VisibleForTesting
import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import java.lang.ref.WeakReference

class DetailNoteFragmentPresenterImpl
@VisibleForTesting constructor(private val notesRepository: NotesRepository) : DetailNoteFragmentPresenter {

    private var viewReference: WeakReference<DetailNoteFragmentPresenter.View>? = null

    override fun attachView(view: DetailNoteFragmentPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

    override fun getView(): DetailNoteFragmentPresenter.View? =
            if (viewReference == null) {
                null
            } else {
                viewReference?.get()
            }

    override fun openNote(noteId: String?) {
        if (null == noteId || noteId.isEmpty()) {
            getView()?.showMissingNote()
            return
        }

        getView()?.setProgressIndicator(true)
        notesRepository.getNote(noteId, object : NotesRepository.GetNoteCallback {
            override fun onNoteLoaded(note: Note?) {
                getView()?.setProgressIndicator(false)

                note?.let {
                    showNote(note)
                } ?: getView()?.showMissingNote()
            }
        })
    }

    private fun showNote(note: Note) {
        val title = note.title
        val description = note.description
        val imageUrl = note.imageUrl

        if (title.isNullOrEmpty()) {
            getView()?.hideTitle()
        } else {
            title?.let { getView()?.showTitle(it) }
        }

        if (description.isNullOrEmpty()) {
            getView()?.hideDescription()
        } else {
            description?.let { getView()?.showDescription(it) }
        }

        imageUrl?.let {
            getView()?.showImage(it)
        } ?: getView()?.hideImage()
    }

}
