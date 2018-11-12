package com.example.naufal.football.Api.ModelView

import com.example.naufal.football.Entity.Match

interface MatchView {

    fun isLoading()
    fun stopLoading()
    fun showMatch(data : List<Match>?)

}