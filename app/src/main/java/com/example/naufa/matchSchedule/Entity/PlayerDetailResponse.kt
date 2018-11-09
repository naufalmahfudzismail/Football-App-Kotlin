package com.example.naufa.matchSchedule.Entity

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
    @field:SerializedName("players")
    val playerDetails: List<PlayerDetail>
)