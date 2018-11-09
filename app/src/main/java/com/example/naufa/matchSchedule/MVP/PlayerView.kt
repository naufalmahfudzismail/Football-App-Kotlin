package com.example.naufa.matchSchedule.MVP

import com.example.naufa.matchSchedule.Entity.Player

interface PlayerView {

    fun isLoading()
    fun stopLoading()
    fun showPlayer(data : List<Player>?)

}