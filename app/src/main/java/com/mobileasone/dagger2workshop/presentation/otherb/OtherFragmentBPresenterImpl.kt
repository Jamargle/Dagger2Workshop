package com.mobileasone.dagger2workshop.presentation.otherb

import java.lang.ref.WeakReference

class OtherFragmentBPresenterImpl
private constructor() : OtherFragmentBPresenter {

    companion object {

        private var INSTANCE: OtherFragmentBPresenter? = null

        fun getInstance(): OtherFragmentBPresenter {
            if (INSTANCE == null) {
                INSTANCE = OtherFragmentBPresenterImpl()
            }
            return INSTANCE as OtherFragmentBPresenter
        }

    }

    private var viewReference: WeakReference<OtherFragmentBPresenter.View>? = null

    override fun attachView(view: OtherFragmentBPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

}
