package com.dnovaes.pokemontcg.commonFeature.views

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel

class TCGCarouselAdapter(
    private val count: Int,
    private val onPopulateImage: (Int, ImageView) -> Unit,
    private val onDragOption: (Int)-> Unit
): Carousel.Adapter {
    override fun count() = count

    override fun populate(view: View?, index: Int) {
        onPopulateImage(index, view as ImageView)
    }

    override fun onNewItem(index: Int) {
        onDragOption(index)
    }
}