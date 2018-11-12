package com.example.naufal.football

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.naufal.football.Adapter.FragmentDetailTeamAdapter
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.TeamPresenter
import com.example.naufal.football.Database.FavoriteTeam
import com.example.naufal.football.Database.database
import com.example.naufal.football.Entity.Team
import com.example.naufal.football.Api.ModelView.TeamListView
import com.example.naufal.football.R.id.add_to_favorite
import com.example.naufal.football.R.menu.menu_detail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import org.jetbrains.anko.db.delete


class DetailTeamActivity : AppCompatActivity(), TeamListView {

    private lateinit var idTeam: String
    private lateinit var pagerTeamAdapter: FragmentDetailTeamAdapter
    private lateinit var teams: Team
    private lateinit var teamPresenter : TeamPresenter
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.title = getString(R.string.team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idTeam = intent.getStringExtra("teamId")
        setFragment()

        val request = ApiRepository()
        val gson = Gson()

        teamPresenter = TeamPresenter(this, request, gson)
        teamPresenter.geDetailTeam(idTeam)

        favoriteState()

    }

    private fun setFragment() {
        pagerTeamAdapter = FragmentDetailTeamAdapter(idTeam, supportFragmentManager)
        team_viewPager.adapter = pagerTeamAdapter
        team_tabLayout.setupWithViewPager(team_viewPager)
    }

    override fun isLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.INVISIBLE
    }

    override fun showTeam(data: List<Team>) {
        teams = Team(data[0].teamId,
            data[0].teamName,
            data[0].strTeamBadge
        )
        data[0].strTeamBadge.let{ Glide.with(this).load(it).into(team_image_detail)}
        team_name_detail.text = data[0].teamName
        team_formed_detail.text = data[0].teamFormedYear
        team_stadium_detail.text = data[0].teamStadium
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
                    if (isFavorite) removeFromFavorite() else addToFavorite()

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
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teams.strTeamBadge,
                    FavoriteTeam.TEAM_NAME to teams.teamId,
                    FavoriteTeam.TEAM_BADGE to teams.teamName)
            }
            Snackbar.make(findViewById(R.id.rl_detail_team),
               getString(R.string.add_match),
                Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.rl_detail_team),
                getString(R.string.error_add_match)+ e.localizedMessage,
                Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})", "id" to idTeam)
            }
            Snackbar.make(findViewById(R.id.rl_detail_team),
                getString(R.string.remove_fav), Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.rl_detail_team),
                getString(R.string.cant_remove) + e.localizedMessage,
                Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
              R.drawable.ic_already_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_add_favorite
            )
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to idTeam)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
