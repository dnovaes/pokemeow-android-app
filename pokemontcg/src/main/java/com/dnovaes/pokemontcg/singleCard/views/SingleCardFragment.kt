package com.dnovaes.pokemontcg.singleCard.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dnovaes.commons.views.BaseFragment
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.databinding.FragmentSingleCardBinding
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
        //findNavController().navigate(R.id.action_SingleCardFragment_to_LauncherFragment)
        binding.btnSingleCard.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun setObservers() {
        viewModel.cardLiveData.observe(viewLifecycleOwner) {
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
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(this.requireContext())
        bottomSheet.setContentView(R.layout.bottomsheet_select_expansion_and_card_layout)
        val cardNumberEditText = bottomSheet.findViewById<TextInputEditText>(R.id.edit_text_card_number)!!
        val cardNumberInputLayout = bottomSheet.findViewById<TextInputLayout>(R.id.input_layout_card_number)!!
        cardNumberInputLayout.setEndIconOnClickListener {
            val cardNumber = cardNumberEditText.text
            viewModel.getCard("xy1-${cardNumber}")
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }
}
