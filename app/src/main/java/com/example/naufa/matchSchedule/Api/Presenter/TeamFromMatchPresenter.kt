package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.TeamResponse
import com.example.naufa.matchSchedule.util.ContextProvider
import com.example.naufa.matchSchedule.MVP.TeamView
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