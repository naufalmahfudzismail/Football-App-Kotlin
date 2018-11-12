package com.example.naufal.football.Api.ModelView

import com.example.naufal.football.Entity.Team

interface TeamView {

    fun isLoading()
    fun stopLoading()
    fun showTeam(data : List<Team>?, dataB : List<Team>?)

}