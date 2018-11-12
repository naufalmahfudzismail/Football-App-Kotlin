package com.example.naufal.football.Api.ModelView

import com.example.naufal.football.Entity.PlayerDetail

interface PlayerDetailView {

    fun isLoading()
    fun stopLoading()
    fun showPlayerDetail(data: List<PlayerDetail>)
}