package com.dnovaes.commons.views

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dnovaes.commons.views.OverlaySpinnerFragment.Companion.OVERLAY_SPINNER_TAG

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

    private val overlaySpinnerFrag = OverlaySpinnerFragment()

    protected fun showLoading() {
        overlaySpinnerFrag.isCancelable = false
        overlaySpinnerFrag.show(childFragmentManager, OVERLAY_SPINNER_TAG)
    }

    protected fun stopLoading() {
        overlaySpinnerFrag.dismiss()
    }
}