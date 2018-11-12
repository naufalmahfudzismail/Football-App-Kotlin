package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.MatchResponse
import com.example.naufal.football.Util.ContextProvider
import com.example.naufal.football.Api.ModelView.MatchView
import com.google.gson.Gson
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
            view.showMatch(data.await().matches)
            view.stopLoading()
        }
    }
}