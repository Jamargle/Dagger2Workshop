package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.addnotes.AddNoteFragment
import com.mobileasone.dagger2workshop.presentation.notedetail.DetailNoteFragment
import com.mobileasone.dagger2workshop.presentation.notelist.NoteListFragment
import com.mobileasone.dagger2workshop.presentation.otherb.OtherFragmentB
import com.mobileasone.dagger2workshop.presentation.otherc.OtherFragmentC
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    // Add bindings for other sub-components here
    @ContributesAndroidInjector(modules = [NoteListFragmentModule::class])
    abstract fun bindNoteListFragment(): NoteListFragment

    @ContributesAndroidInjector(modules = [DetailNoteFragmentModule::class])
    abstract fun bindDetailNoteFragment(): DetailNoteFragment

    @ContributesAndroidInjector(modules = [AddNoteFragmentModule::class])
    abstract fun bindAddNoteFragmentFragment(): AddNoteFragment

    @ContributesAndroidInjector(modules = [OtherFragmentBModule::class])
    abstract fun bindOtherFragmentB(): OtherFragmentB

    @ContributesAndroidInjector(modules = [OtherFragmentCModule::class])
    abstract fun bindOtherFragmentC(): OtherFragmentC

}
