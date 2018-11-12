package com.example.naufal.football.Api.ModelView

import com.example.naufal.football.Entity.Team

interface TeamListView {

    fun isLoading()
    fun stopLoading()
    fun showTeam(data : List<Team>)
}