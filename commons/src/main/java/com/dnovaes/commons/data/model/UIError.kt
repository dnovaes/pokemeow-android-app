package com.dnovaes.commons.data.model

import androidx.annotation.StringRes
import com.dnovaes.commons.R

data class UIError(
    @StringRes val stringRes: Int = R.string.default_ui_error_message,
    val throwable: Throwable? = null
)
