package com.dnovaes.pokemontcg.commonFeature.domain.di

import com.dnovaes.pokemontcg.commonFeature.repository.mapper.TcgMapper
import com.dnovaes.pokemontcg.commonFeature.repository.mapper.TcgMapperInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TcgMapperModule {

    @Provides
    fun providesTcgMapper() : TcgMapperInterface = TcgMapper()
}