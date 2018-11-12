package com.example.naufal.football.Fragment.FavoriteFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufal.football.Adapter.FavoriteTeamsRecyclerAdapter
import com.example.naufal.football.Database.FavoriteTeam
import com.example.naufal.football.Database.database
import com.example.naufal.football.DetailTeamActivity
import com.example.naufal.football.R
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.intentFor

class FavoriteTeamFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: FavoriteTeamsRecyclerAdapter
    private var favoriteTeam: MutableList<FavoriteTeam> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe_fav_team.setOnRefreshListener(this)

        adapter = FavoriteTeamsRecyclerAdapter(this.context!!, favoriteTeam) {
            startActivity(
                intentFor<DetailTeamActivity>("teamId" to it.teamId)
            )
        }

        rcy_fav_team.layoutManager = LinearLayoutManager(activity)
        rcy_fav_team.adapter = adapter
        getTeams()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onRefresh() {
        getTeams()
    }

    private fun getTeams(){
        favoriteTeam.clear()
        activity?.database?.use{
            swipe_fav_team.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}