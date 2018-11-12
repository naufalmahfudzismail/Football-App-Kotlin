package com.example.naufal.football.Entity

import com.google.gson.annotations.SerializedName

data  class MatchResponse(
   @SerializedName("events")
    val matches: List<Match>? = null,

   @SerializedName("event")
    val match: List<Match>? = null,

   @SerializedName("teams")
    val teams: List<Team>? = null
)



