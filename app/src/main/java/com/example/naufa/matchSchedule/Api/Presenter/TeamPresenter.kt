package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.TeamResponse
import com.example.naufa.matchSchedule.MVP.TeamListView
import com.example.naufa.matchSchedule.MVP.TeamView
import com.example.naufa.matchSchedule.util.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(
    private val view: TeamListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun getTeams(leagueSearch: String?) {
        view.isLoading()
        if (leagueSearch.isNullOrEmpty()) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getTeams()),
                        TeamResponse::class.java
                    )
                }
                view.showTeam(data.await().teams)
                view.stopLoading()
            }

        } else {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getTeamSearch(leagueSearch)),
                        TeamResponse::class.java
                    )
                }
                view.showTeam(data.await().teams)
                view.stopLoading()
            }
        }
    }

    fun geDetailTeam(team: String?) {
        view.isLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(SportAPI.getTeam(team)),
                    TeamResponse::class.java
                )
            }
            view.showTeam(data.await().teams)
            view.stopLoading()
        }
    }
}