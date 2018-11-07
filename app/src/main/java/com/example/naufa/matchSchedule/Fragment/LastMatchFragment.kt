package com.example.naufa.matchSchedule.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.naufa.matchSchedule.Adapter.MatchRecyclerAdapter
import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.MatchPresenter
import com.example.naufa.matchSchedule.DetailActivity
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.R
import com.example.naufa.matchSchedule.util.MatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast


class LastMatchFragment : Fragment(), MatchView, SwipeRefreshLayout.OnRefreshListener {



    private lateinit var matchPresenter: MatchPresenter
    private lateinit var adapter: MatchRecyclerAdapter
    private var matches: MutableList<Match> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_layout.setOnRefreshListener(this)
        adapter = MatchRecyclerAdapter(this.context!!, matches) {

            startActivity(
                intentFor<DetailActivity>(
                    "matchId" to it.idEvent, "teamA" to it.idHomeTeam, "teamB" to it.idAwayTeam
                )
            )
        }

        match_last_rcy.layoutManager = LinearLayoutManager(activity)
        match_last_rcy.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        matchPresenter = MatchPresenter(this, request, gson)
        matchPresenter.getMatchList(false)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onRefresh() {
        matchPresenter.getMatchList(false)
    }

    override fun isLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.GONE
    }

    override fun showMatch(data: List<Match>?) {
        swipe_layout.isRefreshing = false
        matches.clear()
        data?.let {
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast(getString(R.string.kosong))
    }

}