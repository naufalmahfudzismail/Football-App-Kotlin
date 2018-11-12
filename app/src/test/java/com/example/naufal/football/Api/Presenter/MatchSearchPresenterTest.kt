package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.Match
import com.example.naufal.football.Entity.MatchResponse
import com.example.naufal.football.Api.ModelView.MatchView
import com.example.naufal.football.Util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchSearchPresenterTest {


    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchSearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchSearchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatch() {

        val matchEvent: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matchEvent)
        val id  = "4328"

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getLastMatch(id)), MatchResponse::class.java
            )
        )
            .thenReturn(response)
       presenter.getMatch(id)

        Mockito.verify(view).isLoading()
        Mockito.verify(view).showMatch(matchEvent)
        Mockito.verify(view).stopLoading()
    }
}