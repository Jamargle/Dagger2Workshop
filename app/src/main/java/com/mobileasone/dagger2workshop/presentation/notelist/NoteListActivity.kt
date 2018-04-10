package com.mobileasone.dagger2workshop.presentation.notelist

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mobileasone.dagger2workshop.R
import com.mobileasone.dagger2workshop.presentation.addnotes.AddNoteActivity
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteActivity
import com.mobileasone.dagger2workshop.presentation.otherb.OtherFragmentB
import com.mobileasone.dagger2workshop.presentation.otherc.OtherFragmentC
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import kotlinx.android.synthetic.main.activity_note_list.bottom_navigation
import kotlinx.android.synthetic.main.activity_note_list.content_frame
import kotlinx.android.synthetic.main.activity_note_list.toolbar
import javax.inject.Inject

class NoteListActivity : AppCompatActivity(), HasFragmentInjector,
        NoteListFragment.Callback,
        OtherFragmentB.Callback,
        OtherFragmentC.Callback {

    companion object {
        private const val SELECTED_ITEM = "key:selected_item"
    }

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        initToolbar()

        selectedItem = savedInstanceState?.getInt(SELECTED_ITEM, 0) ?: 0
        initBottomNavigationView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SELECTED_ITEM, selectedItem)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val homeItem = bottom_navigation.menu.getItem(0)
        if (selectedItem != homeItem.itemId) {
            // select home item
            bottom_navigation.selectedItemId = homeItem.itemId
            selectFragment(homeItem)
        } else {
            super.onBackPressed()
        }
    }

    override fun showAddNote() {
        startActivityForResult(AddNoteActivity.newBundle(this), NoteListFragment.REQUEST_ADD_NOTE)
    }

    override fun showNoteDetails(noteId: String) {
        startActivity(DetailNoteActivity.newBundle(this, noteId))
    }

    override fun updateTitleForFragmentB() {
        Snackbar.make(content_frame, R.string.title_other_b, Snackbar.LENGTH_SHORT).show()
    }

    override fun updateTitleForFragmentC() {
        Snackbar.make(content_frame, R.string.title_other_c, Snackbar.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        val toolbar = toolbar
        setActionBar(toolbar)
    }

    private fun initBottomNavigationView() {
        bottom_navigation.setOnNavigationItemSelectedListener(
                OnNavigationItemSelectedListener { item ->
                    selectFragment(item)
                    return@OnNavigationItemSelectedListener true
                })

        val selectedMenuItem = bottom_navigation.menu.findItem(selectedItem)
        selectedMenuItem?.let {
            selectFragment(it)
        } ?: selectFragment(bottom_navigation.menu.getItem(selectedItem))
    }

    private fun selectFragment(item: MenuItem) {
        selectedItem = item.itemId
        when (selectedItem) {
            R.id.note_list_screen -> showFragment(NoteListFragment.newInstance())
            R.id.another_screen_b -> showFragment(OtherFragmentB.newInstance())
            R.id.another_screen_c -> showFragment(OtherFragmentC.newInstance())
        }
    }

    private fun showFragment(fragment: Fragment) {
        val tag = fragment.javaClass.name
        val fragmentByTag = fragmentManager.findFragmentByTag(tag)
        if (fragmentByTag != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentByTag).commit()
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.content_frame, fragment, tag)
                    .commit()
        }
    }

}
