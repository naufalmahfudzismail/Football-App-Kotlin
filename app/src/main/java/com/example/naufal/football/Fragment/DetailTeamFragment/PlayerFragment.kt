package com.example.naufal.football.Fragment.DetailTeamFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufal.football.Adapter.FragmentDetailTeamAdapter
import com.example.naufal.football.Adapter.PlayerRecyclerAdapter
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.PlayerPresenter
import com.example.naufal.football.DetailPlayerActivity
import com.example.naufal.football.Entity.Player
import com.example.naufal.football.Api.ModelView.PlayerView
import com.example.naufal.football.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.support.v4.intentFor

class PlayerFragment : Fragment(), PlayerView, SwipeRefreshLayout.OnRefreshListener{

    private  lateinit var adapter : PlayerRecyclerAdapter
    private lateinit var playerPresenter : PlayerPresenter
    private var players : MutableList<Player> =  mutableListOf()
    private lateinit var idTeam: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_team_player.setOnRefreshListener(this)

        adapter = PlayerRecyclerAdapter(this.context!!,players){
            startActivity(
                intentFor<DetailPlayerActivity>("idPlayer" to it.idPlayer, "idTeam" to idTeam)
            )
        }

        val bindData = arguments
        idTeam = bindData?.getString(FragmentDetailTeamAdapter.KEY_TEAM_2) ?: "idTeam"

        rcy_team_player.layoutManager = LinearLayoutManager(activity)
        rcy_team_player.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        playerPresenter = PlayerPresenter(this, request, gson)
        playerPresenter.getPlayerList(idTeam)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_player, container, false)
    }

    override fun onRefresh() {
        playerPresenter.getPlayerList(idTeam)
    }

    override fun isLoading() {
       progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.INVISIBLE
    }

    override fun showPlayer(data: List<Player>?) {
        players.clear()
        data?.let { players.addAll(it) }
        adapter.notifyDataSetChanged()
        swipe_team_player.isRefreshing = false
    }
}