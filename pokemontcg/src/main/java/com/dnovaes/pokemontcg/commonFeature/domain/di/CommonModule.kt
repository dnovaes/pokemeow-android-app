package com.dnovaes.pokemontcg.commonFeature.domain.di

import android.content.Context
import com.dnovaes.pokemontcg.commonFeature.domain.monitoring.AnalyticsConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Singleton
    @Provides
    fun providesAnalyticsConfig(
        @ApplicationContext applicationContext: Context
    ) : AnalyticsConfiguration {
        val config = AnalyticsConfiguration()
        config.setup(applicationContext)
        return config
    }
}