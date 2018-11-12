package com.example.naufal.football.Fragment.MatchFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.naufal.football.Adapter.MatchRecyclerAdapter
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.MatchPresenter
import com.example.naufal.football.DetailMatchActivity
import com.example.naufal.football.Entity.Match
import com.example.naufal.football.R
import com.example.naufal.football.Api.ModelView.MatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class NextMatchFragment : Fragment(), MatchView, SwipeRefreshLayout.OnRefreshListener {


    private lateinit var matchPresenter: MatchPresenter
    private lateinit var adapter: MatchRecyclerAdapter
    private var matches: MutableList<Match> = mutableListOf()
    private var leagues : String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_layout.setOnRefreshListener(this)

        adapter = MatchRecyclerAdapter(this.context!!, matches) {

            startActivity(
                intentFor<DetailMatchActivity>(
                    "matchId" to it.idEvent, "teamA" to it.idHomeTeam, "teamB" to it.idAwayTeam
                )
            )
        }

        match_next_rcy.layoutManager = LinearLayoutManager(activity)
        match_next_rcy.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        matchPresenter = MatchPresenter(this, request, gson)
        matchPresenter.getMatchList(true, getString(R.string.match_english_pemier_league))

        val spinnerItemsLeague = resources.getStringArray(R.array.league)
        val spinnerAdapterLeague = ArrayAdapter(ctx, android.support.design.R.layout.support_simple_spinner_dropdown_item, spinnerItemsLeague)
        spinner_next.adapter = spinnerAdapterLeague

        spinner_next.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if ( spinner_next.selectedItem == getString(R.string.english_pemier_league)) {

                    leagues =  getString(R.string.match_english_pemier_league)

                    matchPresenter.getMatchList(true ,
                        getString(R.string.match_english_pemier_league))

                    swipe_layout.isRefreshing = true
                    matchPresenter.getMatchList(true ,
                        getString(R.string.match_english_pemier_league))

                }
                if (spinner_next.selectedItem == getString(R.string.english_league_championship)) {

                    leagues = getString(R.string.match_english_league_championship)

                    matchPresenter.getMatchList(true,
                        getString(R.string.match_english_league_championship))

                    swipe_layout.isRefreshing = true
                    matchPresenter.getMatchList(true,
                        getString(R.string.match_english_league_championship))
                }
                if (spinner_next.selectedItem == getString(R.string.german_bundes_liga)) {

                    leagues = getString(R.string.match_german_bundes_liga)

                    matchPresenter.getMatchList(true,
                        getString(R.string.match_german_bundes_liga))

                    swipe_layout.isRefreshing = true
                    matchPresenter.getMatchList(true,
                        getString(R.string.match_german_bundes_liga))

                }
                if (spinner_next.selectedItem == getString(R.string.italian_serie_A)) {

                    leagues =  getString(R.string.match_italian_serie_A)

                    matchPresenter.getMatchList(true,
                        getString(R.string.match_italian_serie_A))

                    swipe_layout.isRefreshing = true
                    matchPresenter.getMatchList(true,
                        getString(R.string.match_italian_serie_A))

                }
                if (spinner_next.selectedItem == getString(R.string.french_ligue_1)) {

                    leagues = getString(R.string.match_french_ligue_1)

                    matchPresenter.getMatchList(true,
                        getString(R.string.match_french_ligue_1))

                    swipe_layout.isRefreshing = true
                    matchPresenter.getMatchList(true,
                        getString(R.string.match_french_ligue_1))

                }

            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onRefresh() {
        matchPresenter.getMatchList(true, leagues)
    }

    override fun isLoading() {
        swipe_layout.isRefreshing = true
    }

    override fun stopLoading() {
        swipe_layout.isRefreshing = false
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