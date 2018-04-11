package com.mobileasone.dagger2workshop.presentation.otherc

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileasone.dagger2workshop.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class OtherFragmentC : Fragment(),
        OtherFragmentCPresenter.View {

    companion object {
        fun newInstance() = OtherFragmentC()
    }

    @Inject lateinit var presenter: OtherFragmentCPresenter
    private lateinit var callback: Callback

    override fun onAttach(activity: Activity?) {
        AndroidInjection.inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_other_c, container, false)

    override fun onViewCreated(
            view: View?,
            savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        callback = activity as Callback
        callback.updateTitleForFragmentC()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        callback = activity as Callback
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    interface Callback {

        fun updateTitleForFragmentC()

    }

}
