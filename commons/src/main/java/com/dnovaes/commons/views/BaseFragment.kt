package com.dnovaes.commons.views

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment: Fragment() {

    fun createWithFactory(
        create: () -> ViewModel
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return create() as T
            }
        }
    }

    private lateinit var progressBar: ProgressBar

    protected fun showLoading(view: ViewGroup?) {
        progressBar = ProgressBar(requireActivity(), null, android.R.attr.progressBarStyleLarge)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE
        view?.addView(progressBar)
    }

    protected fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}