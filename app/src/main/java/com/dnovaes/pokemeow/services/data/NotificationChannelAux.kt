package com.dnovaes.pokemeow.services.data

import android.app.NotificationManager

data class NotificationChannelAux(
    val id: String,
    val name: String,
    val importance: Int,
    val sound: String? = null
)

