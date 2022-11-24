package com.dnovaes.pokemontcg.singleCard.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.utilities.extensions.makesNotDraggable
import com.dnovaes.commons.utilities.extensions.onDone
import com.dnovaes.commons.views.BaseFragment
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetsInterface
import com.dnovaes.pokemontcg.databinding.FragmentSingleCardBinding
import com.dnovaes.pokemontcg.singleCard.data.model.hasDoneLoadingPkmSets
import com.dnovaes.pokemontcg.singleCard.viewmodels.SingleCardViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
                        showBottomSheet(it)
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

    private fun showBottomSheet(tcgSets: TcgSetsInterface) {
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.bottomsheet_select_expansion_and_card_layout)
        val cardNumberEditText = bottomSheet.findViewById<TextInputEditText>(R.id.edit_text_card_number)!!
        val cardNumberInputLayout = bottomSheet.findViewById<TextInputLayout>(R.id.input_layout_card_number)!!

        bottomSheet.makesNotDraggable()

        val setsCarousel = bottomSheet.findViewById<Carousel>(R.id.bsheet_expset_carousel)!!
        setupCarousel(setsCarousel, tcgSets)

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

    private fun setupCarousel(expSetCarousel: Carousel, sets: TcgSetsInterface) {
        val context = this
        expSetCarousel.setAdapter(object: Carousel.Adapter {
            override fun count() = sets.collection.size

            override fun populate(view: View?, index: Int) {
                val imgView = view as ImageView
                val currSet = sets.collection[index]
                //println("logd populating $index with set: ${currSet.id}, ${currSet.name}, with view: ${view.hashCode()}")
                Glide.with(context)
                    .load(currSet.images.logo)
                    .into(imgView)
            }

            override fun onNewItem(index: Int) {
                viewModel.selectSet(index)
            }
        })
    }

    private fun processBottomSheetCardNumber(editText: EditText) {
        val cardNumber = editText.text.toString()
        viewModel.getCard(cardNumber)
        editText.setText("")
    }
}
