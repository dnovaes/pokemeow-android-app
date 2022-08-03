package com.dnovaes.commons.data.model

import androidx.annotation.StringRes

data class UIError(
    @StringRes val stringRes: Int,
    val throwable: Throwable
)
