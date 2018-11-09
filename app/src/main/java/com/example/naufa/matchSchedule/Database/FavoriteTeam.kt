package com.example.naufa.matchSchedule.Database

data class FavoriteTeam(
    val id: Long? = null,
    val teamId: String? = null,
    val teamName: String? = null,
    val teamBadge: String? = null) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID_FAVORITE_TEAM: String = "ID_FAVORITE_TEAM"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}