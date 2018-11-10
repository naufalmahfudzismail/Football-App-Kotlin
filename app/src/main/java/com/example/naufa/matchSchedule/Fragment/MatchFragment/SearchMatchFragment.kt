package com.example.naufa.matchSchedule.Fragment.MatchFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufa.matchSchedule.Adapter.MatchRecyclerAdapter
import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.Presenter.MatchSearchPresenter
import com.example.naufa.matchSchedule.DetailMatchActivity
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.MVP.MatchView
import com.example.naufa.matchSchedule.R
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_match_search.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class SearchMatchFragment : Fragment(), MatchView ,SwipeRefreshLayout.OnRefreshListener, MaterialSearchBar.OnSearchActionListener {


    private lateinit var adapter : MatchRecyclerAdapter
    private lateinit var searchPresenter : MatchSearchPresenter
    private var matches: MutableList<Match> = mutableListOf()
    private var title : String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe_search_match.setOnRefreshListener(this)
        search_match.setOnSearchActionListener(this)

        adapter = MatchRecyclerAdapter(this.context!!, matches) {
            startActivity(
                intentFor<DetailMatchActivity>(
                    "matchId" to it.idEvent, "teamA" to it.idHomeTeam, "teamB" to it.idAwayTeam
                )
            )
        }

        rcy_search_match.layoutManager = LinearLayoutManager(activity)
        rcy_search_match.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        searchPresenter = MatchSearchPresenter(this, request, gson)
        searchPresenter.getMatch(title)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_search, container, false)
    }

    override fun onRefresh() {
        if (swipe_search_match.isRefreshing)
            swipe_search_match.isRefreshing = false
        startRefresh(title)
    }

    override fun isLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.GONE
    }

    override fun showMatch(data: List<Match>?) {
        swipe_search_match.isRefreshing = false
        matches.clear()
        data?.let {
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast(getString(R.string.kosong))
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        title = text.toString()
        startRefresh(title)
    }

    private fun startRefresh(key: String){

        if (swipe_search_match.isRefreshing) return
        swipe_search_match.isRefreshing = true
        searchPresenter.getMatch(key)
    }



    override fun onButtonClicked(buttonCode: Int) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }



}