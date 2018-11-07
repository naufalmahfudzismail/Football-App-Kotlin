package com.example.naufa.matchSchedule.Api

import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.Entity.Team
import com.example.naufa.matchSchedule.Entity.TeamResponse
import com.example.naufa.matchSchedule.util.MatchView
import com.example.naufa.matchSchedule.util.TeamView
import com.example.naufa.matchSchedule.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTesting {

    @Mock
    private
    lateinit var view: TeamView

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
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }



    @Test
    fun geDetailTeamList() {

        val teamsA: MutableList<Team> = mutableListOf()
        val teamsB : MutableList<Team> = mutableListOf()
        val responseA = TeamResponse(teamsA)
        val responseB = TeamResponse(teamsB)
        val idX = "1234"
        val idY= "4321"

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(SportAPI.getTeam(idX)),
            TeamResponse::class.java
        )).thenReturn(responseA)

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(SportAPI.getTeam(idY)),
            TeamResponse::class.java
        )).thenReturn(responseB)

        presenter.geDetailTeamList(idX, idY)

        Mockito.verify(view).isLoading()
        Mockito.verify(view).showTeam(teamsA, teamsB)
        Mockito.verify(view).stopLoading()
    }
}