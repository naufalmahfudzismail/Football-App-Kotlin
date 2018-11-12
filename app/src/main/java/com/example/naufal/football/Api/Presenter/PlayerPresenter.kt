package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.PlayerResponse
import com.example.naufal.football.Api.ModelView.PlayerView
import com.example.naufal.football.Util.ContextProvider
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