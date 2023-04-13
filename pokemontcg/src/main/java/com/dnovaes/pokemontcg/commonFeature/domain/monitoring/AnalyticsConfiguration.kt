package com.dnovaes.pokemontcg.commonFeature.domain.monitoring

import android.content.Context
import com.dnovaes.pokemontcg.BuildConfig
import com.newrelic.agent.android.NewRelic

class AnalyticsConfiguration {

    fun setup(context: Context) {
        NewRelic.withApplicationToken(BuildConfig.NEWRELIC_KEY).start(context)
    }
}