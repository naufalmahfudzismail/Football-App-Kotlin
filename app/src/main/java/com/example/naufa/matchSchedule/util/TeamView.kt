package com.example.naufa.matchSchedule.util

import com.example.naufa.matchSchedule.Entity.Team

interface TeamView {

    fun isLoading()
    fun stopLoading()
    fun showTeam(data : List<Team>?, dataB : List<Team>?)

}