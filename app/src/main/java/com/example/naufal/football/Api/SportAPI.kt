package com.example.naufal.football.Api


object SportAPI {

    fun getLastMatch(idLeague : String):String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=$idLeague"
    }

    fun getNextMatch(idLeague : String):String{
        return "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=$idLeague"
    }

    fun getMatchSearch(searchInput: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=$searchInput"
    }

    fun getMatch(matchId : String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=$matchId"
    }

    fun getTeam(teamId : String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$teamId"
    }

    fun getTeams():String{
        return "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?s=Soccer&c=Spain"
    }

    fun getTeamsLeague(league : String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=$league"
    }

    fun getPlayerTeam(teamId: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=$teamId"
    }

    fun getPlayerDetail(playerId: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id=$playerId"
    }


    fun getTeamSearch(searchInput: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=$searchInput"
    }

}