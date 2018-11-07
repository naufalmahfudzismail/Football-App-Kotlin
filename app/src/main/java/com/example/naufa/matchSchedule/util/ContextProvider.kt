package com.example.naufa.matchSchedule.util

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class ContextProvider {
    open val main: CoroutineContext by lazy { UI }
}