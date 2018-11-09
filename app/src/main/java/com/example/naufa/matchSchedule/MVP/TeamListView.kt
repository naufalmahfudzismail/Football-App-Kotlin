package com.example.naufa.matchSchedule.MVP

import com.example.naufa.matchSchedule.Entity.Team

interface TeamListView {

    fun isLoading()
    fun stopLoading()
    fun showTeam(data : List<Team>)
}