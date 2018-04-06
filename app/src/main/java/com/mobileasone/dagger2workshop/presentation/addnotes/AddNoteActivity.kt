package com.mobileasone.dagger2workshop.presentation.addnotes

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mobileasone.dagger2workshop.R
import kotlinx.android.synthetic.main.activity_add_note.toolbar

class AddNoteActivity : AppCompatActivity(),
        AddNoteFragment.Callback {

    companion object {
        private const val ADD_NOTE_FRAGMENT_TAG = "add_note_fragment_tag"

        fun newBundle(context: Context) = Intent(context, AddNoteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initToolbar()
        if (null == savedInstanceState) {
            initFragment(AddNoteFragment.newInstance())
        }
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

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {

        handleInAddNoteFragment()?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun requestStoragePermissions(requestCode: Int) {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode)
    }

    private fun initToolbar() {
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initFragment(fragment: AddNoteFragment) {
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment, ADD_NOTE_FRAGMENT_TAG)
                .commit()
    }

    private fun handleInAddNoteFragment() =
            fragmentManager?.findFragmentByTag(ADD_NOTE_FRAGMENT_TAG) as AddNoteFragment?

}
