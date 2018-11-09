package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.PlayerDetailResponse
import com.example.naufa.matchSchedule.MVP.PlayerDetailView
import com.example.naufa.matchSchedule.util.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: ContextProvider = ContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.isLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(SportAPI.getPlayerDetail(idTeam)),
                    PlayerDetailResponse::class.java
                )
            }
            view.showPlayerDetail(data.await().playerDetails)
            view.stopLoading()
        }
    }
}