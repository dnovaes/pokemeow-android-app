package com.dnovaes.commons.data.model

data class UIViewState<T: UIModelInterface>(
    val state: UIDataStateInterface,
    val result: T? = null,
    val error: UIError? = null
)

interface UIDataStateInterface

enum class UIDataState: UIDataStateInterface {
    IDLE,
    LOADING,
    DONE
}

fun <T: UIModelInterface>UIViewState<T>.withResult(result: T): UIViewState<T> {
    return this.copy(result = result, error = error)
}

fun <T: UIModelInterface>UIViewState<T>.withError(errorParam: UIError?): UIViewState<T> {
    return this.copy(result = result, error = errorParam)
}
