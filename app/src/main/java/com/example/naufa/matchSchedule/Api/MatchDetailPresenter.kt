package com.example.naufa.matchSchedule.Api

import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.util.ContextProvider
import com.example.naufa.matchSchedule.util.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchDetailPresenter (private val view: MatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: ContextProvider = ContextProvider()
) {

    fun getDetailMatch(matchId: String?) {
        view.isLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(SportAPI.getMatch(matchId)),
                    MatchResponse::class.java
                )
            }
            view.showMatch(data.await().events)
            view.stopLoading()
        }
    }
}