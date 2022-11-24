package com.dnovaes.commons.utilities.extensions

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Hide Keyboard
            val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            callback.invoke()
            true
        }
        false
    }
}