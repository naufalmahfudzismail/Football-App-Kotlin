package com.example.naufa.matchSchedule.MVP

import com.example.naufa.matchSchedule.Entity.Match

interface MatchView {

    fun isLoading()
    fun stopLoading()
    fun showMatch(data : List<Match>?)

}