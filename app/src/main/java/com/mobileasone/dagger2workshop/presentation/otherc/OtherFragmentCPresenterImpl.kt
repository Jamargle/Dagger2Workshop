package com.mobileasone.dagger2workshop.presentation.otherc

import java.lang.ref.WeakReference

class OtherFragmentCPresenterImpl : OtherFragmentCPresenter {

    private var viewReference: WeakReference<OtherFragmentCPresenter.View>? = null

    override fun attachView(view: OtherFragmentCPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

}
