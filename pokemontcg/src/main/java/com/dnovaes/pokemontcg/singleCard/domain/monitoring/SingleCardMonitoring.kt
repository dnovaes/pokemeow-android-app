package com.dnovaes.pokemontcg.singleCard.domain.monitoring

import android.util.Log
import com.newrelic.agent.android.NewRelic

class SingleCardMonitoring {

    companion object {
        const val SINGLE_CARD_FLOW = "FEATURE: POKEMONTCG, FLOW: SINGLE_CARD"
    }

    fun logCardSuccessResponse(action: String, extraParams: Map<String, String>) {
        val message = "$SINGLE_CARD_FLOW | Service Call '$action' with state SUCCESS got response with " +
                "requesting card number: '${extraParams["cardNumber"]}' and " +
                "expansionId: '${extraParams["expansionId"]}'"
        logMessage(message)
    }

    fun logCardFailureResponse(action: String, extraParams: Map<String, String>, exception: Throwable?) {
        var message = "$SINGLE_CARD_FLOW | Service Call '$action' with state FAILURE got response with " +
                "requesting card number: '${extraParams["cardNumber"]}' and " +
                "expansionId: '${extraParams["expansionId"]}'"
        exception?.let {
            message += ", with exception: ${it.localizedMessage} and cause: ${it.cause}"
        }
        logMessage(message)
    }

    private fun logMessage(message: String) {
        Log.d("DEBUG", message)
        NewRelic.recordBreadcrumb(message)
    }

}