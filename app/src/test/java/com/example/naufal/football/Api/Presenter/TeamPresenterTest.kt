package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.Team
import com.example.naufal.football.Entity.TeamResponse
import com.example.naufal.football.Api.ModelView.TeamListView
import com.example.naufal.football.Util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private
    lateinit var teamView: TeamListView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(teamView, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getTeams() {

        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getTeams()),
                TeamResponse::class.java
            )
        ).thenReturn(response)

        presenter.getTeams("")

        Mockito.verify(teamView).isLoading()
        Mockito.verify(teamView).showTeam(teams)
        Mockito.verify(teamView).stopLoading()
    }

    @Test
    fun geDetailTeam() {

        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "1234"

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getTeam(id)),
                TeamResponse::class.java
            )
        ).thenReturn(response)

        presenter.getTeams(id)

        Mockito.verify(teamView).isLoading()
        Mockito.verify(teamView).showTeam(teams)
        Mockito.verify(teamView).stopLoading()
    }

}


