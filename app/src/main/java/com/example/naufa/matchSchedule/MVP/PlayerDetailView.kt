package com.example.naufa.matchSchedule.MVP

import com.example.naufa.matchSchedule.Entity.PlayerDetail

interface PlayerDetailView {

    fun isLoading()
    fun stopLoading()
    fun showPlayerDetail(data: List<PlayerDetail>)
}