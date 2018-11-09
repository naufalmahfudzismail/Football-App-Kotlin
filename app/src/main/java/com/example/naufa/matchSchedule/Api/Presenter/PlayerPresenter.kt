package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.PlayerResponse
import com.example.naufa.matchSchedule.MVP.PlayerView
import com.example.naufa.matchSchedule.util.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter (private val view: PlayerView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson,
                       private val context: ContextProvider = ContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.isLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(SportAPI.getPlayerTeam(idTeam)),
                    PlayerResponse::class.java
                )
            }
            view.showPlayer(data.await().playerTeams)
            view.stopLoading()
        }
    }
}