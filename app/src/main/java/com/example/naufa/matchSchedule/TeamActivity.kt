package com.example.naufa.matchSchedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.naufa.matchSchedule.Adapter.TeamRecyclerAdapter
import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.Presenter.TeamPresenter
import com.example.naufa.matchSchedule.Entity.Team
import com.example.naufa.matchSchedule.MVP.TeamListView
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.intentFor

class TeamActivity : AppCompatActivity(), TeamListView, MaterialSearchBar.OnSearchActionListener,
    SwipeRefreshLayout.OnRefreshListener {


    private lateinit var teamPresenter: TeamPresenter
    private lateinit var adapter: TeamRecyclerAdapter
    private var teams: MutableList<Team> = mutableListOf()
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        supportActionBar?.title = getString(R.string.teams_title)

        adapter = TeamRecyclerAdapter(this, teams){

            startActivity(
                intentFor<DetailTeamActivity>(
                    "teamId" to it.teamId
                )
            )
        }

        team_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.MatchMenu -> {
                    startActivity(
                        intentFor<MatchActivity>()
                    )
                }
                R.id.TeamMenu -> {
                    startActivity(
                        intentFor<TeamActivity>()
                    )
                }
                R.id.FavoriteMenu -> {
                    startActivity(
                        intentFor<FavoriteActivity>()
                    )
                }

            }
            true
        }


        rcy_team.layoutManager = LinearLayoutManager(this)
        rcy_team.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        teamPresenter = TeamPresenter(this, request, gson)
        teamPresenter.getTeams(title)

    }

    override fun onRefresh() {
        if (swipe_search_team.isRefreshing)
            swipe_search_team.isRefreshing = false
        startRefresh()
    }


    override fun isLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.INVISIBLE
    }

    override fun showTeam(data: List<Team>) {

        swipe_search_team.isRefreshing = false
        teams.clear()
        data.let {
            teams.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onSearchConfirmed(text: CharSequence?) {
        title = text.toString()
        startRefresh()
    }

    private fun startRefresh(){

        if (swipe_search_team.isRefreshing) return
        swipe_search_team.isRefreshing = true
        teamPresenter.getTeams(title)
    }

    override fun onButtonClicked(buttonCode: Int) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

}
