package com.example.naufa.matchSchedule.Fragment.MatchFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.design.R.layout.support_simple_spinner_dropdown_item
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.naufa.matchSchedule.Adapter.MatchRecyclerAdapter
import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.Presenter.MatchPresenter
import com.example.naufa.matchSchedule.DetailMatchActivity
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.R
import com.example.naufa.matchSchedule.MVP.MatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import com.example.naufa.matchSchedule.R.array.league
import com.example.naufa.matchSchedule.R.string.*


class LastMatchFragment : Fragment(), MatchView, SwipeRefreshLayout.OnRefreshListener {



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

        match_last_rcy.layoutManager = LinearLayoutManager(activity)
        match_last_rcy.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        matchPresenter = MatchPresenter(this, request, gson)
        matchPresenter.getMatchList(false, getString(match_english_pemier_league))

        val spinnerItemsLeague = resources.getStringArray(league)
        val spinnerAdapterLeague = ArrayAdapter(ctx, support_simple_spinner_dropdown_item, spinnerItemsLeague)
        spinner_last.adapter = spinnerAdapterLeague

       spinner_last.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

           override fun onNothingSelected(parent: AdapterView<*>?) {

           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

               if ( spinner_last.selectedItem == getString(english_pemier_league)) {

                   leagues =  getString(match_english_pemier_league)

                   matchPresenter.getMatchList(false ,
                       getString(match_english_pemier_league))

                   swipe_layout.isRefreshing = true
                   matchPresenter.getMatchList(false , getString(match_english_pemier_league))


               }
               if (spinner_last.selectedItem == getString(english_league_championship)) {

                   leagues = getString(match_english_league_championship)

                   matchPresenter.getMatchList(false,
                       getString(match_english_league_championship))

                   swipe_layout.isRefreshing = true
                   matchPresenter.getMatchList(false,
                       getString(match_english_league_championship))

               }
               if (spinner_last.selectedItem == getString(german_bundes_liga)) {

                   leagues = getString(match_german_bundes_liga)

                   matchPresenter.getMatchList(false,
                       getString(match_german_bundes_liga))

                   swipe_layout.isRefreshing = true
                   matchPresenter.getMatchList(false,
                       getString(match_german_bundes_liga))

               }
               if (spinner_last.selectedItem == getString(italian_serie_A)) {

                   leagues =  getString(match_italian_serie_A)

                   matchPresenter.getMatchList(false,
                       getString(match_italian_serie_A))

                   swipe_layout.isRefreshing = true
                   matchPresenter.getMatchList(false,
                       getString(match_italian_serie_A))

               }
               if (spinner_last.selectedItem == getString(french_ligue_1)) {

                   leagues = getString(match_french_ligue_1)

                   matchPresenter.getMatchList(false,
                       getString(match_french_ligue_1))

                   swipe_layout.isRefreshing = true
                   matchPresenter.getMatchList(false,
                       getString(match_french_ligue_1))
               }

           }

       }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onRefresh() {
        matchPresenter.getMatchList(false, leagues)
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