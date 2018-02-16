package com.mobileasone.dagger2workshop.presentation.notelist

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileasone.dagger2workshop.R
import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl
import com.mobileasone.dagger2workshop.domain.Note
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import kotlinx.android.synthetic.main.activity_note_list.fab_add_notes
import kotlinx.android.synthetic.main.fragment_notes.notes_list
import kotlinx.android.synthetic.main.fragment_notes.refresh_layout

class NoteListFragment : Fragment(),
        NoteListFragmentPresenter.View,
        NotesAdapter.OnNoteItemListener {

    companion object {
        internal const val REQUEST_ADD_NOTE = 1

        fun newInstance() = NoteListFragment()
    }

    private lateinit var callback: Callback
    private lateinit var presenter: NoteListFragmentPresenter
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_notes, container, false)

    override fun onViewCreated(
            view: View?,
            savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFAB()
        initSwipeToRefresh()
    }

    override fun onStart() {
        super.onStart()
        presenter = NoteListFragmentPresenterImpl.getInstance(instantiateNotesRepository())
        presenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        callback = activity as Callback
    }

    override fun onResume() {
        super.onResume()
        presenter.loadNotes(false)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?) {

        if (REQUEST_ADD_NOTE == requestCode
                && Activity.RESULT_OK == resultCode) {

            view?.let {
                Snackbar.make(it, getString(R.string.successfully_saved_note_message), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun setProgressIndicator(active: Boolean) {
        view?.let {
            val swipeRefreshLayout: SwipeRefreshLayout = refresh_layout
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = active }
        }
    }

    override fun showNotes(notes: List<Note>) {
        adapter.setList(notes)
    }

    override fun showAddNote() {
        callback.showAddNote()
    }

    override fun showNoteDetailUi(noteId: String) {
        callback.showNoteDetails(noteId)
    }

    override fun onNoteClicked(note: Note) {
        presenter.openNoteDetails(note)
    }

    private fun initRecyclerView() {
        adapter = NotesAdapter(ArrayList(), this)
        val noteListView: RecyclerView = notes_list
        noteListView.adapter = adapter
        noteListView.setHasFixedSize(true)
        val numColumns = activity.resources.getInteger(R.integer.num_notes_columns)
        noteListView.layoutManager = GridLayoutManager(activity, numColumns)
    }

    private fun initFAB() {
        val fab = activity.fab_add_notes
        fab?.setImageResource(R.drawable.ic_add)
        fab?.setOnClickListener({
            presenter.addNewNote()
        })
    }

    private fun initSwipeToRefresh() {
        val swipeRefreshLayout: SwipeRefreshLayout = refresh_layout
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark))
        swipeRefreshLayout.setOnRefreshListener { presenter.loadNotes(true) }
    }

    private fun instantiateNotesRepository(): NotesRepository = NotesLocalMemoryRepository.getInstance(instantiateNotesNetworkRepository())

    private fun instantiateNotesNetworkRepository() = NotesServiceApiImpl.getInstance()

    interface Callback {

        fun showAddNote()

        fun showNoteDetails(noteId: String)

    }

}
