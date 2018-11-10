package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.util.ContextProvider
import com.example.naufa.matchSchedule.MVP.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun getMatchList(isNext: Boolean?, league : String) {
        view.isLoading()
        if (!isNext!!) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getLastMatch(league)),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().matches)
                view.stopLoading()
            }
        }

        else{
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getNextMatch(league)),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().matches)
                view.stopLoading()
            }
        }
    }
}