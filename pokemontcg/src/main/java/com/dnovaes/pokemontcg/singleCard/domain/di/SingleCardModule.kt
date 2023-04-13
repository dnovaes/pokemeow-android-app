package com.dnovaes.pokemontcg.singleCard.domain.di

import com.dnovaes.pokemontcg.commonFeature.domain.monitoring.AnalyticsConfiguration
import com.dnovaes.pokemontcg.commonFeature.repository.TcgRepositoryInterface
import com.dnovaes.pokemontcg.commonFeature.repository.mapper.TcgMapperInterface
import com.dnovaes.pokemontcg.singleCard.domain.monitoring.SingleCardMonitoring
import com.dnovaes.pokemontcg.singleCard.domain.repository.SingleCardUseCase
import com.dnovaes.pokemontcg.singleCard.domain.repository.SingleCardUseCaseInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapper
import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapperInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SingleCardModule {

    @Provides
    fun providesSingleCardMapper(): SingleCardMapperInterface = SingleCardMapper()

    @Provides
    fun providesSingleCardUseCase(
        pkmTcgRepository: TcgRepositoryInterface,
        singleCardMapper: SingleCardMapperInterface,
        tcgMapper: TcgMapperInterface
    ): SingleCardUseCaseInterface = SingleCardUseCase(
        pkmTcgRepository,
        singleCardMapper,
        tcgMapper
    )

    @Provides
    fun providesSingleCardMonitoring(
        analyticsConfig: AnalyticsConfiguration
    ): SingleCardMonitoring = SingleCardMonitoring()
}
