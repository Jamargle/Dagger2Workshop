package com.mobileasone.dagger2workshop.presentation.otherc

import java.lang.ref.WeakReference

class OtherFragmentCPresenterImpl
private constructor() : OtherFragmentCPresenter {

    companion object {

        private var INSTANCE: OtherFragmentCPresenter? = null

        fun getInstance(): OtherFragmentCPresenter {
            if (INSTANCE == null) {
                INSTANCE = OtherFragmentCPresenterImpl()
            }
            return INSTANCE as OtherFragmentCPresenter
        }

    }

    private var viewReference: WeakReference<OtherFragmentCPresenter.View>? = null

    override fun attachView(view: OtherFragmentCPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

}
