package com.example.naufal.football.Entity

import com.google.gson.annotations.SerializedName

class PlayerResponse(
    @field:SerializedName("player")
                      val playerTeams: List<Player>
)

