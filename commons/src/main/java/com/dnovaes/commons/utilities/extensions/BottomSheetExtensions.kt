package com.dnovaes.commons.utilities.extensions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun BottomSheetDialog.makesNotDraggable() {
    val behavior = this.behavior
    behavior.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED;
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            //do nothing
        }
    })
}