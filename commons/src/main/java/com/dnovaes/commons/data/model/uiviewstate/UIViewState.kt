package com.dnovaes.commons.data.model.uiviewstate

import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.UIModelInterface

data class UIViewState<T: UIModelInterface>(
    val state: UIDataStateInterface,
    val process: UIDataProcessInterface,
    val result: T? = null,
    val error: UIError? = null
)

fun <T: UIModelInterface> UIViewState<T>.withResult(result: T?): UIViewState<T> {
    return this.copy(result = result, error = error)
}

fun <T: UIModelInterface> UIViewState<T>.withError(errorParam: UIError?): UIViewState<T> {
    return this.copy(result = result, error = errorParam)
}
