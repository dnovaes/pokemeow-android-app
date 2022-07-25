package com.dnovaes.pokemontcg.singleCard.views

import com.dnovaes.commons.data.network.LoggerInterceptor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnovaes.commons.views.BaseFragment
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.databinding.FragmentSecondBinding
import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.PokemonTcgRepository
import com.dnovaes.pokemontcg.singleCard.viewmodels.SingleCardViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SingleCardFragmentWithoutDI : BaseFragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val json = Json { ignoreUnknownKeys = true }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(LoggerInterceptor())
            .build()
    }

    protected fun buildRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(buildClient())
            .build()
    }

    private val singleCardViewModel: SingleCardViewModel by viewModels{
        val tcgService = buildRetrofit().create(PokemonTcgAPIInterface::class.java)
        val tcgRepository = PokemonTcgRepository(tcgService)
        createWithFactory {
            SingleCardViewModel(tcgRepository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindElements()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindElements() {
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.btnSingleCard.setOnClickListener {
            singleCardViewModel.getCard("xy1-1")
        }
    }
}