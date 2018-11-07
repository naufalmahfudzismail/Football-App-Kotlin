package com.example.naufa.matchSchedule.Entity

import com.google.gson.annotations.SerializedName

data  class MatchResponse(
   @SerializedName("events")
    val events: List<Match>? = null,

   @SerializedName("event")
    val event: List<Match>? = null,

   @SerializedName("teams")
    val teams: List<Team>? = null
)



