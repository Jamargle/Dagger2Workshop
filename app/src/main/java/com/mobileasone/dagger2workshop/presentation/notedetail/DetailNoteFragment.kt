package com.mobileasone.dagger2workshop.presentation.notedetail

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mobileasone.dagger2workshop.R
import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.util.ImageViewWithImagePath
import kotlinx.android.synthetic.main.fragment_note_detail.note_detail_description
import kotlinx.android.synthetic.main.fragment_note_detail.note_detail_image
import kotlinx.android.synthetic.main.fragment_note_detail.note_detail_title

class DetailNoteFragment : Fragment(),
        DetailNoteFragmentPresenter.View {

    companion object {
        private const val ARGUMENT_NOTE_ID = "NOTE_ID"

        fun newInstance(noteId: String) = DetailNoteFragment().apply {
            val arguments = Bundle()
            arguments.putString(ARGUMENT_NOTE_ID, noteId)
            setArguments(arguments)
        }
    }

    private var detailImage: ImageView? = null
    private var detailTitle: TextView? = null
    private var detailDescription: TextView? = null

    private lateinit var presenter: DetailNoteFragmentPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = DetailNoteFragmentPresenterImpl.getInstance(instantiateNotesRepository())
    }

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_note_detail, container, false)

    override fun onViewCreated(
            view: View?,
            savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        detailTitle = note_detail_title
        detailDescription = note_detail_description
        detailImage = note_detail_image
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        val noteId = arguments.getString(ARGUMENT_NOTE_ID)
        presenter.openNote(noteId)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun setProgressIndicator(active: Boolean) {
        if (active) {
            detailTitle?.text = ""
            detailDescription?.text = getString(R.string.loading)
        }
    }

    override fun hideDescription() {
        detailDescription?.visibility = View.GONE
    }

    override fun hideTitle() {
        detailTitle?.visibility = View.GONE
    }

    override fun showDescription(description: String) {
        detailDescription?.visibility = View.VISIBLE
        detailDescription?.text = description
    }

    override fun showTitle(title: String) {
        detailTitle?.visibility = View.VISIBLE
        detailTitle?.text = title
    }

    override fun showImage(imageUrl: String) {
        detailImage?.visibility = View.VISIBLE
        (detailImage as ImageViewWithImagePath).setImageByImagePath(activity, imageUrl)
    }

    override fun hideImage() {
        detailImage?.setImageDrawable(null)
        detailImage?.visibility = View.GONE
    }

    override fun showMissingNote() {
        detailTitle?.text = ""
        detailDescription?.text = getString(R.string.no_data)
    }

    private fun instantiateNotesRepository(): NotesRepository = NotesLocalMemoryRepository.getInstance(instantiateNotesNetworkRepository())

    private fun instantiateNotesNetworkRepository() = NotesServiceApiImpl.getInstance()

}
