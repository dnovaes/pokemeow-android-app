package com.dnovaes.pokemontcg.singleCard.data.model

import com.dnovaes.commons.data.model.UIModelInterface
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.data.model.uiviewstate.UIViewState

//set methods

fun <T: UIModelInterface> UIViewState<T>.asLoadingPkmCardSets(): UIViewState<T>
        = this.copy(process = SingleCardUIDataProcess.LOADING_CARD_SETS)

fun <T: UIModelInterface> UIViewState<T>.asLoadingPkmSingleCard(): UIViewState<T>
        = this.copy(process = SingleCardUIDataProcess.LOADING_SINGLE_CARD)

fun <T: UIModelInterface> UIViewState<T>.asPickingPkmCardSet(): UIViewState<T>
        = this.copy(process = SingleCardUIDataProcess.SELECTING_CARD_SET)

// check method states

fun <T: UIModelInterface> UIViewState<T>.hasDoneLoadingPkmSets(): Boolean
        = ((process == SingleCardUIDataProcess.LOADING_CARD_SETS)
        && (state == UIDataState.DONE))
