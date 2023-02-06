package com.dnovaes.pokemontcg.singleCard.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.utilities.extensions.setNotDraggable
import com.dnovaes.commons.utilities.extensions.onDone
import com.dnovaes.commons.views.BaseFragment
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.SingleCardSetsInterface
import com.dnovaes.pokemontcg.commonFeature.views.TCGCarouselAdapter
import com.dnovaes.pokemontcg.databinding.FragmentSingleCardBinding
import com.dnovaes.pokemontcg.singleCard.data.model.hasDoneLoadingPkmSets
import com.dnovaes.pokemontcg.singleCard.viewmodels.SingleCardViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleCardFragment : BaseFragment() {

    private var _binding: FragmentSingleCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SingleCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindElements()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindElements() {
        binding.btnSingleCard.setOnClickListener {
            viewModel.getExpansionSets()
        }
    }

    private fun setObservers() {
        observeExpansionSetsData()
        observeSingleCardLoad()
    }

    private fun observeExpansionSetsData() {
        viewModel.setsLiveData.observe(viewLifecycleOwner) { uiData ->
            when {
                uiData.hasDoneLoadingPkmSets() -> {
                    uiData.result?.let {
                        showBottomSheet(it, it.selectedIdName)
                    }
                    stopLoading()
                }
                uiData.state == UIDataState.PROCESSING -> showLoading()
            }
        }
    }

    private fun observeSingleCardLoad() {
        viewModel.cardLiveData.observe(viewLifecycleOwner) {
            when (it.state) {
                UIDataState.DONE -> {
                    stopLoading()
                    it.result?.let { card ->
                        Glide.with(this)
                            .load(card.images.small)
                            .placeholder(R.drawable.pkm_card_back)
                            .centerCrop()
                            .into(binding.imgSingleCard)
                    }
                    it.error?.let { error ->
                        Snackbar.make(binding.root, getString(error.stringRes), Snackbar.LENGTH_LONG).show()
                    }
                }
                UIDataState.PROCESSING -> showLoading()
            }
        }
    }

    private fun showBottomSheet(tcgSets: SingleCardSetsInterface, selectedSetId: String?) {
        val bottomSheet = buildSetsBottomSheet(tcgSets)

        val cardNumberEditText = bottomSheet.findViewById<TextInputEditText>(R.id.edit_text_card_number)!!
        val cardNumberInputLayout = bottomSheet.findViewById<TextInputLayout>(R.id.input_layout_card_number)!!

        cardNumberEditText.onDone {
            processBottomSheetCardNumber(cardNumberEditText)
            bottomSheet.dismiss()
        }

        cardNumberInputLayout.setEndIconOnClickListener {
            processBottomSheetCardNumber(cardNumberEditText)
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }

    private fun buildSetsBottomSheet(tcgSets: SingleCardSetsInterface) = BottomSheetDialog(requireContext()).apply {
        setContentView(R.layout.bottomsheet_select_expansion_and_card_layout)
        setNotDraggable()
        findViewById<Carousel>(R.id.bsheet_expset_carousel)?.let {
            setupCarousel(it, tcgSets.collection)
        }
    }

    private fun setupCarousel(expSetCarousel: Carousel, sets: List<TcgSetInterface>) {
        val context = this
        val adapter = TCGCarouselAdapter(
            sets.size,
            onPopulateImage = { index, imgView ->
                val currSet = sets[index]
                Glide.with(context)
                    .load(currSet.images.logo)
                    .into(imgView)
            },
            onSlideOption = { index ->
                println("logd slided to index: $index")
                viewModel.selectSet(index)
            }
        )
        expSetCarousel.setAdapter(adapter)
    }

    private fun processBottomSheetCardNumber(editText: EditText) {
        val cardNumber = editText.text.toString()
        viewModel.getCard(cardNumber)
        editText.setText("")
    }
}
