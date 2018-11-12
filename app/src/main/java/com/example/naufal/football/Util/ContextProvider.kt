package com.example.naufal.football.Util

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class ContextProvider {
    open val main: CoroutineContext by lazy { UI }
}