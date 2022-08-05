package com.dnovaes.pokemontcg.singleCard.domain.di

import com.dnovaes.commons.data.network.DispatcherInterface
import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.PokemonTcgRepository
import com.dnovaes.pokemontcg.singleCard.domain.repository.TcgRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TcgNetworkDomainModule {

    @Provides
    fun providesTcgRepository(
        tcgService: PokemonTcgAPIInterface,
        dispatcher: DispatcherInterface
    ) : TcgRepositoryInterface {
        return PokemonTcgRepository(tcgService, dispatcher)
    }
}
