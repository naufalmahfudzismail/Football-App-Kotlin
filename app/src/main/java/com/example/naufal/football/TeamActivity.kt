package com.example.naufal.football

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.naufal.football.Adapter.TeamRecyclerAdapter
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.TeamPresenter
import com.example.naufal.football.Entity.Team
import com.example.naufal.football.Api.ModelView.TeamListView
import com.example.naufal.football.R.array.league
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.intentFor

class TeamActivity : AppCompatActivity(), TeamListView, MaterialSearchBar.OnSearchActionListener,
    SwipeRefreshLayout.OnRefreshListener {


    private lateinit var teamPresenter: TeamPresenter
    private lateinit var adapter: TeamRecyclerAdapter
    private var teams: MutableList<Team> = mutableListOf()
    private var title: String = ""
    private var isSearch : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        supportActionBar?.title = getString(R.string.teams_title)

        swipe_search_team.setOnRefreshListener(this)
        search_team.setOnSearchActionListener(this)

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
        team_bottom_navigation.menu.findItem(R.id.TeamMenu).isChecked = true


        rcy_team.layoutManager = LinearLayoutManager(this)
        rcy_team.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        teamPresenter = TeamPresenter(this, request, gson)
        teamPresenter.getTeams(isSearch, getString(R.string.english_pemier_league))

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        spinner_team.adapter = spinnerAdapter

        spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                title = spinner_team.selectedItem.toString()
                isSearch  = false
                swipe_search_team.isRefreshing = true
                teamPresenter.getTeams(isSearch, title)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    override fun onRefresh() {
        if (swipe_search_team.isRefreshing)
            swipe_search_team.isRefreshing = false
        startRefresh()
    }


    override fun isLoading() {
        swipe_search_team.isRefreshing = true
    }

    override fun stopLoading() {
        swipe_search_team.isRefreshing = false
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
        isSearch = true
        startRefresh()
    }

    private fun startRefresh(){

        if (swipe_search_team.isRefreshing) return
        swipe_search_team.isRefreshing = true
        teamPresenter.getTeams(isSearch, title)
    }

    override fun onButtonClicked(buttonCode: Int) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

}
