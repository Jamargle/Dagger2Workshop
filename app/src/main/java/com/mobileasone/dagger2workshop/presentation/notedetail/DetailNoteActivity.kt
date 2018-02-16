package com.mobileasone.dagger2workshop.presentation.notedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mobileasone.dagger2workshop.R
import kotlinx.android.synthetic.main.activity_note_detail.toolbar


class DetailNoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE_ID = "NOTE_ID"

        fun newBundle(
                context: Context,
                noteId: String) =
                Intent(context, DetailNoteActivity::class.java).apply {
                    putExtra(EXTRA_NOTE_ID, noteId)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        initToolbar()

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID)
        initFragment(DetailNoteFragment.newInstance(noteId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initFragment(detailFragment: DetailNoteFragment) {
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, detailFragment)
                .commit()
    }

}
