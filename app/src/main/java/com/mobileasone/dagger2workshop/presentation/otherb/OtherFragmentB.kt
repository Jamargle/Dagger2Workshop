package com.mobileasone.dagger2workshop.presentation.otherb

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileasone.dagger2workshop.R

class OtherFragmentB : Fragment(),
        OtherFragmentBPresenter.View {

    companion object {
        fun newInstance() = OtherFragmentB()
    }

    private lateinit var presenter: OtherFragmentBPresenter
    private lateinit var callback: Callback

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_other_b, container, false)

    override fun onViewCreated(
            view: View?,
            savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        presenter = OtherFragmentBPresenterImpl.getInstance()
        callback = activity as Callback
        callback.updateTitleForFragmentB()
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

        fun updateTitleForFragmentB()

    }

}
