package com.example.naufal.football.Util

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : ContextProvider() {
    override val main: CoroutineContext = Unconfined
}