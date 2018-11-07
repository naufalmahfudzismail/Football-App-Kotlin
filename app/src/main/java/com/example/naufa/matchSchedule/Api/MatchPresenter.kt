package com.example.naufa.matchSchedule.Api

import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.util.ContextProvider
import com.example.naufa.matchSchedule.util.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun getMatchList(isNext: Boolean?) {
        view.isLoading()
        if (!isNext!!) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getLastMatch()),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().events)
                view.stopLoading()
            }
        }

        else{
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getNextMatch()),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().events)
                view.stopLoading()
            }
        }
    }
}