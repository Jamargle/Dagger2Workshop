package com.mobileasone.dagger2workshop.presentation.otherb

import java.lang.ref.WeakReference

class OtherFragmentBPresenterImpl : OtherFragmentBPresenter {

    private var viewReference: WeakReference<OtherFragmentBPresenter.View>? = null

    override fun attachView(view: OtherFragmentBPresenter.View) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference?.clear()
        viewReference = null
    }

}
