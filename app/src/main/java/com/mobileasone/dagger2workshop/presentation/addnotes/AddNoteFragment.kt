package com.mobileasone.dagger2workshop.presentation.addnotes

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mobileasone.dagger2workshop.R
import com.mobileasone.dagger2workshop.data.local.NotesLocalMemoryRepository
import com.mobileasone.dagger2workshop.data.network.NotesServiceApiImpl
import com.mobileasone.dagger2workshop.domain.repositories.NotesRepository
import com.mobileasone.dagger2workshop.util.GeneralConstants
import com.mobileasone.dagger2workshop.util.ImageViewWithImagePath
import kotlinx.android.synthetic.main.fragment_add_note.add_note_description
import kotlinx.android.synthetic.main.fragment_add_note.add_note_image_thumbnail
import kotlinx.android.synthetic.main.fragment_add_note.add_note_title
import java.io.File

class AddNoteFragment : Fragment(),
        AddNotePresenter.View {

    companion object {
        private const val REQUEST_CODE_IMAGE_CAPTURE = 0x1001
        private const val REQUEST_CODE_STORAGE_PERMISSION = 11

        fun newInstance(): AddNoteFragment {
            return AddNoteFragment()
        }
    }

    private lateinit var presenter: AddNotePresenter
    private lateinit var callback: Callback

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var imageThumbnail: ImageView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_add_note, container, false)
        setHasOptionsMenu(true)
        retainInstance = true
        return root
    }

    override fun onStart() {
        super.onStart()
        presenter = AddNotePresenterImpl.getInstance(instantiateNotesRepository())
        presenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        title = add_note_title
        description = add_note_description
        imageThumbnail = add_note_image_thumbnail

        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab_add_notes)
        fab?.setImageResource(R.drawable.ic_done)
        fab?.setOnClickListener({
            presenter.saveNote(
                    title.text.toString(),
                    description.text.toString(),
                    (imageThumbnail as ImageViewWithImagePath).getFilePath())
        })

        callback = activity as Callback
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.take_picture -> {
            if (hasStoragePermissions()) {
                presenter.takePicture()
            } else {
                requestStoragePermissions()
            }
            true
        }
        else -> false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_addnote_options_menu_actions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?) {

        presenter.attachView(this)
        // If an image is received, display it on the ImageView.
        if (REQUEST_CODE_IMAGE_CAPTURE == requestCode && Activity.RESULT_OK == resultCode) {
            presenter.imageAvailable()
        } else {
            presenter.imageCaptureFailed()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {

        presenter.attachView(this)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                REQUEST_CODE_STORAGE_PERMISSION -> presenter.takePicture()
            }
        }
    }

    override fun showEmptyNoteError() {
        title.let { Snackbar.make(it, getString(R.string.empty_note_message), Snackbar.LENGTH_LONG).show() }
    }

    override fun showNotesList() {
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    override fun openCamera(saveTo: String) {
        // Open the camera to take a picture.
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Check if there is a camera app installed to handle our Intent
        if (takePictureIntent.resolveActivity(activity?.packageManager) != null) {
            val uriForFile = activity?.let {
                FileProvider.getUriForFile(
                        it,
                        it.applicationContext.packageName + GeneralConstants.CONTENT_AUTHORITY,
                        File(saveTo))
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
            startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE)
        } else {
            Snackbar.make(title, getString(R.string.cannot_connect_to_camera_message),
                    Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showImagePreview(imagePath: String) {
        imageThumbnail.visibility = View.VISIBLE
        (imageThumbnail as ImageViewWithImagePath).setImageByImagePath(activity, imagePath)
    }

    override fun showImageError() {
        Snackbar.make(title, getString(R.string.cannot_connect_to_camera_message),
                Snackbar.LENGTH_SHORT).show()
    }

    private fun instantiateNotesRepository(): NotesRepository = NotesLocalMemoryRepository.getInstance(instantiateNotesNetworkRepository())

    private fun instantiateNotesNetworkRepository() = NotesServiceApiImpl.getInstance()

    private fun hasStoragePermissions() =
            activity?.let {
                ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } == PackageManager.PERMISSION_GRANTED

    private fun requestStoragePermissions() {
        callback.requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSION)
    }

    interface Callback {

        fun requestStoragePermissions(requestCode: Int)

    }

}
