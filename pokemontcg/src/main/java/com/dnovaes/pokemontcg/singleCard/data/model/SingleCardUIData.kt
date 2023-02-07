package com.dnovaes.pokemontcg.singleCard.data.model

import com.dnovaes.commons.data.model.UIModelInterface
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.data.model.uiviewstate.UIViewState
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSet
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.Card
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.SingleCardTCGSets

//set methods

fun <T: UIModelInterface> UIViewState<Card>.asLoadingPkmCardSets(): UIViewState<Card>
        = this.copy(process = SingleCardUIDataProcess.LOADING_CARD_SETS)

//LoadingSingleCard

fun UIViewState<Card>.isProcessingLoadingSingleCard() =
    this.state == UIDataState.PROCESSING && this.process == SingleCardUIDataProcess.LOADING_SINGLE_CARD

fun UIViewState<Card>.isDoneLoadingSingleCard() =
    this.state == UIDataState.DONE && this.process == SingleCardUIDataProcess.LOADING_SINGLE_CARD

fun UIViewState<Card>.asProcessingLoadingPkmSingleCard(): UIViewState<Card> {
    return this.copy(
        process = SingleCardUIDataProcess.LOADING_SINGLE_CARD,
        state = UIDataState.PROCESSING
    )
}

fun UIViewState<Card>.asDoneLoadingPkmSingleCard(): UIViewState<Card> {
    return this.copy(
        process = SingleCardUIDataProcess.LOADING_SINGLE_CARD,
        state = UIDataState.DONE
    )
}

//LoadingCardSets

fun UIViewState<SingleCardTCGSets>.isDoneLoadingPkmCardSets() =
    state == UIDataState.DONE && process == SingleCardUIDataProcess.LOADING_CARD_SETS

fun UIViewState<SingleCardTCGSets>.asProcessingLoadingPkmCardSet(): UIViewState<SingleCardTCGSets>
        = this.copy(
            process = SingleCardUIDataProcess.LOADING_CARD_SETS,
            state = UIDataState.PROCESSING
        )

fun UIViewState<SingleCardTCGSets>.asDoneLoadingPkmCardSet(): UIViewState<SingleCardTCGSets> {
    return this.copy(
        process = SingleCardUIDataProcess.LOADING_CARD_SETS,
        state = UIDataState.DONE
    )
}

//SelectingCardSet

fun UIViewState<SingleCardTCGSets>.asDoneSelectingPkmCardSet(): UIViewState<SingleCardTCGSets> {
    return this.copy(
        process = SingleCardUIDataProcess.SELECTING_CARD_SET,
        state = UIDataState.DONE
    )
}

