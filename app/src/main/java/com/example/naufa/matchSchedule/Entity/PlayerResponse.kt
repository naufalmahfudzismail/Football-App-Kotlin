package com.example.naufa.matchSchedule.Entity

import com.google.gson.annotations.SerializedName

class PlayerResponse(
    @field:SerializedName("player")
                      val playerTeams: List<Player>
)

