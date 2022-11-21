package com.dnovaes.pokemontcg.singleCard.data.model

import com.dnovaes.commons.data.model.uiviewstate.UIDataProcessInterface


enum class SingleCardUIDataProcess: UIDataProcessInterface {
    SELECTING_CARD_SET,
    LOADING_SINGLE_CARD,
    LOADING_CARD_SETS
}
