package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.MatchResponse
import com.example.naufal.football.Api.ModelView.MatchView
import com.example.naufal.football.Util.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchSearchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun getMatch(title: String?) {
        view.isLoading()

        if(title.isNullOrEmpty()) {

            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getLastMatch("4328")),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().matches)
                view.stopLoading()
            }
        }
        else {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getMatchSearch(title)),
                        MatchResponse::class.java
                    )
                }
                view.showMatch(data.await().match)
                view.stopLoading()
            }
        }
    }
}