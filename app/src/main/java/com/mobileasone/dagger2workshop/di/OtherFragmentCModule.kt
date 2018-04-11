package com.mobileasone.dagger2workshop.di

import com.mobileasone.dagger2workshop.presentation.otherc.OtherFragmentCPresenter
import com.mobileasone.dagger2workshop.presentation.otherc.OtherFragmentCPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * This module will provide dependencies for OtherFragmentC
 */
@Module
class OtherFragmentCModule {

    @Provides
    fun otherFragmentCPresenter(): OtherFragmentCPresenter {
        return OtherFragmentCPresenterImpl()
    }

}
