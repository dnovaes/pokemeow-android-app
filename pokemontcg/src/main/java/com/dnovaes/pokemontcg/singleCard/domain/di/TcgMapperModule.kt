package com.dnovaes.pokemontcg.singleCard.domain.di

import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapper
import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapperInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TcgMapperModule {

    @Provides
    fun providesSingleCardMapper() : SingleCardMapperInterface = SingleCardMapper()
}
