package com.example.naufa.matchSchedule.Api

import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.Entity.TeamResponse
import com.example.naufa.matchSchedule.util.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun geDetailTeamList(teamA: String?, teamB: String?) {
        view.isLoading()

        async(UI) {
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