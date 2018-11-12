package com.example.naufal.football.Api.ModelView

import com.example.naufal.football.Entity.Player

interface PlayerView {

    fun isLoading()
    fun stopLoading()
    fun showPlayer(data : List<Player>?)

}