package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.TeamResponse
import com.example.naufal.football.Util.ContextProvider
import com.example.naufal.football.Api.ModelView.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamFromMatchPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun geDetailTeamList(teamA: String?, teamB: String?) {
        view.isLoading()

        async(context.main) {
            val dataA = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(SportAPI.getTeam(teamA)),
                    TeamResponse::class.java
                )
            }
            val dataB = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(SportAPI.getTeam(teamB)),
                    TeamResponse::class.java
                )
            }
            view.showTeam(dataA.await().teams, dataB.await().teams)
            view.stopLoading()
        }
    }


}