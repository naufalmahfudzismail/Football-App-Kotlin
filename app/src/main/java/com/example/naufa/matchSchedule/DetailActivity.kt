package com.example.naufa.matchSchedule

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.MatchDetailPresenter
import com.example.naufa.matchSchedule.Api.TeamPresenter
import com.example.naufa.matchSchedule.Database.Favorite
import com.example.naufa.matchSchedule.Database.database
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.Entity.Team
import com.example.naufa.matchSchedule.R.drawable.ic_add_favorite
import com.example.naufa.matchSchedule.R.drawable.ic_already_favorite
import com.example.naufa.matchSchedule.R.id.add_to_favorite
import com.example.naufa.matchSchedule.R.menu.menu_detail
import com.example.naufa.matchSchedule.util.MatchView
import com.example.naufa.matchSchedule.util.TeamView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), MatchView, TeamView {

    private lateinit var idTeamA: String
    private lateinit var idTeamB: String
    private lateinit var idMatch: String
    private lateinit var match: Match

    private lateinit var teamA: Team
    private lateinit var teamB: Team

    private lateinit var teamPresenter: TeamPresenter
    private lateinit var matchDetailPresenter: MatchDetailPresenter
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idMatch = intent.getStringExtra("matchId")
        idTeamA = intent.getStringExtra("teamA")
        idTeamB = intent.getStringExtra("teamB")

        val request = ApiRepository()
        val gson = Gson()

        teamPresenter = TeamPresenter(this, request, gson)
        matchDetailPresenter = MatchDetailPresenter(this, request, gson)

        teamPresenter.geDetailTeamList(idTeamA, idTeamB)
        matchDetailPresenter.getDetailMatch(idMatch)

        favoriteState()
    }

    override fun isLoading() {

        progress_detail.visibility = View.VISIBLE

    }

    override fun stopLoading() {

        progress_detail.visibility = View.GONE

    }

    override fun showMatch(data: List<Match>?) {

        match = Match(
            data?.get(0)?.idEvent,
            data?.get(0)?.strHomeTeam,
            data?.get(0)?.strAwayTeam,
            data?.get(0)?.intHomeScore,
            data?.get(0)?.intAwayScore,
            data?.get(0)?.dateEvent,
            data?.get(0)?.strHomeLineupGoalkeeper,
            data?.get(0)?.strAwayLineupGoalkeeper,
            data?.get(0)?.strHomeGoalDetails,
            data?.get(0)?.strAwayGoalDetails,
            data?.get(0)?.intHomeShots,
            data?.get(0)?.intAwayShots,
            data?.get(0)?.strHomeLineupDefense,
            data?.get(0)?.awayDefense,
            data?.get(0)?.strAwayLineupDefense,
            data?.get(0)?.strAwayLineupMidfield,
            data?.get(0)?.strHomeLineupForward,
            data?.get(0)?.strAwayLineupForward,
            data?.get(0)?.strHomeLineupSubstitutes,
            data?.get(0)?.strAwayLineupSubstitutes,
            data?.get(0)?.strHomeFormation,
            data?.get(0)?.strAwayFormation,
            data?.get(0)?.strTeamBadge,
            data?.get(0)?.idHomeTeam,
            data?.get(0)?.idAwayTeam
        )

        home_name.text = match.strHomeTeam
        home_score_match.text = match.intHomeScore
        home_goals.text = match.strHomeGoalDetails
        home_goalkeeper.text = match.strHomeLineupGoalkeeper
        home_shots.text = match.intHomeShots
        home_defense.text = match.strHomeLineupDefense
        home_forward.text = match.strHomeLineupForward
        home_substitutes.text = match.strHomeLineupSubstitutes
        home_midfield.text = match.strAwayLineupDefense

        away_name.text = match.strAwayTeam
        away_score_match.text = match.intAwayScore
        away_goals.text = match.strAwayGoalDetails
        away_goalkeeper.text = match.strAwayLineupGoalkeeper
        away_shot.text = match.intAwayShots
        away_defense.text = match.awayDefense
        away_forward.text = match.strAwayLineupForward
        away_substitutes.text = match.strAwayLineupSubstitutes
        away_midfield.text = match.strAwayLineupMidfield
        match_date.text = match.dateEvent

    }

    override fun showTeam(data: List<Team>?, dataB: List<Team>?) {
        teamA = Team(data?.get(0)?.strTeamBadge)
        teamB = Team(dataB?.get(0)?.strTeamBadge)
        Glide.with(this).load(data?.get(0)?.strTeamBadge).into(img_home)
        Glide.with(this).load(dataB?.get(0)?.strTeamBadge).into(img_away)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menu_detail, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                try {
                    if (isFavorite) {
                        removeFromFavorite()
                    } else {
                        addToFavorite()
                    }

                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    toast(getString(R.string.alert_loading))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to match.idEvent,
                    Favorite.STR_HOME_TEAM to match.strHomeTeam,
                    Favorite.STR_AWAY_TEAM to match.strAwayTeam,
                    Favorite.INT_HOME_SCORE to match.intHomeScore,
                    Favorite.INT_AWAY_SCORE to match.intAwayScore,
                    Favorite.DATE_EVENT to match.dateEvent,
                    Favorite.STR_HOME_LINEUP_GOALKEEPER to match.strHomeLineupGoalkeeper,
                    Favorite.STR_AWAY_LINEUP_GOALKEEPER to match.strAwayLineupGoalkeeper,
                    Favorite.STR_HOME_GOAL_DETAILS to match.strHomeGoalDetails,
                    Favorite.STR_AWAY_GOAL_DETAILS to match.strAwayGoalDetails,
                    Favorite.INT_HOME_SHOTS to match.intHomeShots,
                    Favorite.INT_AWAY_SHOTS to match.intAwayShots,
                    Favorite.STR_HOME_LINEUP_DEFENSE to match.strHomeLineupDefense,
                    Favorite.AWAY_DEFENSE to match.awayDefense,
                    Favorite.STR_AWAY_LINEUP_DEFENSE to match.strAwayLineupDefense,
                    Favorite.STR_AWAY_LINEUP_MIDFIELD to match.strAwayLineupMidfield,
                    Favorite.STR_HOME_LINEUP_FORWARD to match.strHomeLineupForward,
                    Favorite.STR_AWAY_LINEUP_FORWARD to match.strAwayLineupForward,
                    Favorite.STR_HOME_LINEUP_SUBSTITUTES to match.strHomeLineupSubstitutes,
                    Favorite.STR_AWAY_LINEUP_SUBSTITUTES to match.strAwayLineupSubstitutes,
                    Favorite.STR_HOME_FORMATION to match.strHomeFormation,
                    Favorite.STR_AWAY_FORMATION to match.strAwayFormation,
                    Favorite.STR_TEAM_BADGE to match.strTeamBadge,
                    Favorite.ID_HOME_TEAM to match.idHomeTeam,
                    Favorite.ID_AWAY_TEAM to match.idAwayTeam
                )
            }
            Snackbar.make(
                findViewById(R.id.ll_detail),
                getString(R.string.add_match), Snackbar.LENGTH_SHORT
            ).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(
                findViewById(R.id.ll_detail),
                getString(R.string.error_add_match), Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {idEvent})", "idEvent" to idMatch)
            }
            Snackbar.make(
                findViewById(R.id.ll_detail),
                getString(R.string.hapus_match), Snackbar.LENGTH_SHORT
            ).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(
                findViewById(R.id.ll_detail),
                getString(R.string.remove_match_error), Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setFavorite() {
        if (!isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
                this,
                ic_add_favorite
            )
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
                this,
                ic_already_favorite
            )
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(ID_EVENT = {idEvent})", "idEvent" to idMatch)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}
