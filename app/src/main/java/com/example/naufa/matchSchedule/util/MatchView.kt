package com.example.naufa.matchSchedule.util

import com.example.naufa.matchSchedule.Entity.Match

interface MatchView {

    fun isLoading()
    fun stopLoading()
    fun showMatch(data : List<Match>?)

}