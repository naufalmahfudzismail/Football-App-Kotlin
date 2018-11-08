package com.example.naufa.matchSchedule.Api


object SportAPI {

    fun getLastMatch():String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
    }

    fun getNextMatch():String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
    }

    fun getMatch(matchId : String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=$matchId"
    }

    fun getTeam(teamId : String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$teamId"
    }

    fun getDetailTeam(idTeam: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/4012986/lookupteam.php?id=$idTeam"
    }

    fun getPlayerTeam(teamId: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/4012986/lookup_all_players.php?id=$teamId"
    }

    fun getPlayerDetail(playerId: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/4012986/lookupplayer.php?id=$playerId"
    }

    fun getEventSearch(searchInput: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/4012986/searchevents.php?e=$searchInput"
    }

    fun getTeamSearch(searchInput: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/4012986/searchteams.php?t=$searchInput"
    }



}