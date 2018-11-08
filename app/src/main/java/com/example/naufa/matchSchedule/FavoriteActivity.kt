package com.example.naufa.matchSchedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.example.naufa.matchSchedule.Adapter.FavoriteMatchRecyclerAdapter
import com.example.naufa.matchSchedule.Database.Favorite
import com.example.naufa.matchSchedule.Database.database
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.intentFor


class FavoriteActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    private lateinit var adapter: FavoriteMatchRecyclerAdapter
    private var favoriteMatch: MutableList<Favorite> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = getString(R.string.fav_matches)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fav_swipe_layout.setOnRefreshListener(this)


        adapter = FavoriteMatchRecyclerAdapter(this, favoriteMatch) {
            startActivity(
                intentFor<DetailMatchActivity>(
                    "matchId" to it.idEvent, "teamA" to it.idHomeTeam, "teamB" to it.idAwayTeam
                )
            )
        }

        fav_match_rcy.layoutManager = LinearLayoutManager(this)
        fav_match_rcy.adapter = adapter
        getMatches()
    }

    override fun onRefresh() {
        favoriteMatch.clear()
        getMatches()
    }

    private fun getMatches(){
        this.database.use{
            fav_swipe_layout.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
