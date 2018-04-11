package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.otherb.OtherFragmentBPresenter
import com.mobileasone.dagger2workshop.presentation.otherb.OtherFragmentBPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * This module will provide dependencies for OtherFragmentB
 */
@Module
class OtherFragmentBModule {

    @Provides
    fun otherFragmentBPresenter(): OtherFragmentBPresenter {
        return OtherFragmentBPresenterImpl()
    }

}
