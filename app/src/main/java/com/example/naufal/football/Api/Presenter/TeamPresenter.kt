package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.TeamResponse
import com.example.naufal.football.Api.ModelView.TeamListView
import com.example.naufal.football.Util.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(
    private val view: TeamListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: ContextProvider = ContextProvider()
) {

    fun getTeams(isSearch : Boolean, league: String?) {
        view.isLoading()
        if (!isSearch) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(
                        apiRepository
                            .doRequest(SportAPI.getTeamsLeague(league)),
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
                            .doRequest(SportAPI.getTeamSearch(league)),
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