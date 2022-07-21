package com.dnovaes.pokemontcg.singleCard.views

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
import com.dnovaes.pokemontcg.singleCard.network.PokemonTcgAPI
import com.dnovaes.pokemontcg.singleCard.repository.PokemonTcgRepository
import com.dnovaes.pokemontcg.singleCard.viewmodels.SingleCardViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SingleCardFragment : BaseFragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val json = Json { ignoreUnknownKeys = true }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("X-Api-Key", "3079204f-ecb6-425e-9332-eed64e3608e1")
                        .build()

                    println("logd ==== REQUEST ====")
                    println("logd request: $request")
                    println("logd body: ${request.body}")
                    println("logd headers: ${request.headers}")

                    val response = chain.proceed(request)
                    println("logd ==== RESPONSE ====")
                    println("logd response: $response")
                    println("logd body: ${response.body}")
                    println("logd headers: ${response.headers}")
                    return response
                }
            })
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
        val tcgService = buildRetrofit().create(PokemonTcgAPI::class.java)
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