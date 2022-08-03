package com.dnovaes.commons.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherInterface {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
}

class TcgDispatcher: DispatcherInterface {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val computation: CoroutineDispatcher = Dispatchers.Default
}