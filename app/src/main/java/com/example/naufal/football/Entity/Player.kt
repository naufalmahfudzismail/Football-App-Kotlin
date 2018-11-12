package com.example.naufal.football.Entity

import com.google.gson.annotations.SerializedName

data class Player (
    @SerializedName("idPlayer")
    var idPlayer: String? = null,

    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null
)