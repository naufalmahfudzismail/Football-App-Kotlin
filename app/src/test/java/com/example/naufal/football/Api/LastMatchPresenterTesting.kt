package com.example.naufal.football.Api

import com.example.naufal.football.Api.Presenter.MatchPresenter
import com.example.naufal.football.Entity.Match
import com.example.naufal.football.Entity.MatchResponse
import com.example.naufal.football.Api.ModelView.MatchView
import com.example.naufal.football.Util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LastMatchPresenterTesting {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {

        val matchEvent: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matchEvent)
        val id  = "4328"

        `when`(gson.fromJson(apiRepository
            .doRequest(SportAPI.getLastMatch(id)), MatchResponse::class.java))
            .thenReturn(response)
        presenter.getMatchList(false, id)

        verify(view).isLoading()
        verify(view).showMatch(matchEvent)
        verify(view).stopLoading()
    }
}