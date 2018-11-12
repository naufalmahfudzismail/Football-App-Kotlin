package com.example.naufal.football.Fragment.FavoriteFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufal.football.Adapter.FavoriteMatchRecyclerAdapter
import com.example.naufal.football.Database.FavoriteMatch
import com.example.naufal.football.Database.database
import com.example.naufal.football.DetailMatchActivity
import com.example.naufal.football.R
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.intentFor

class FavoriteMatchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: FavoriteMatchRecyclerAdapter
    private var favoriteMatch: MutableList<FavoriteMatch> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe_fav_match.setOnRefreshListener(this)

        adapter = FavoriteMatchRecyclerAdapter(this.context!!, favoriteMatch) {
            startActivity(
                intentFor<DetailMatchActivity>(
                    "matchId" to it.idEvent, "teamA" to it.idHomeTeam, "teamB" to it.idAwayTeam
                )
            )
        }

        rcy_fav_match.layoutManager = LinearLayoutManager(activity)
        rcy_fav_match.adapter = adapter
        getMatches()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onRefresh() {
        getMatches()
    }

    private fun getMatches(){
        favoriteMatch.clear()
        activity?.database?.use{
            swipe_fav_match.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }


}