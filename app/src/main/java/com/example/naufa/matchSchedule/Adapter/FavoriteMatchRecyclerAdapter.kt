package com.example.naufa.matchSchedule.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufa.matchSchedule.Database.Favorite
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.R
import kotlinx.android.synthetic.main.card_schedule.view.*

class FavoriteMatchRecyclerAdapter(private val context : Context, private val matches : List<Favorite>, private val listener : (Favorite) -> Unit ) :
    RecyclerView.Adapter<FavoriteMatchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return matches.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMatch(matches[position], listener, context)
    }

    class ViewHolder (private val containerView: View) : RecyclerView.ViewHolder(containerView){


        @SuppressLint("SetTextI18n")
        fun bindMatch(match : Favorite, listener : (Favorite) -> Unit, context: Context){

            if(match.intHomeScore == null){
                match.intHomeScore = ""
                match.intAwayScore = ""
            }

            containerView.match_date.text = match.dateEvent
            containerView.teamA.text = match.strHomeTeam
            containerView.teamB.text = match.strAwayTeam
            containerView.score.text = match.intHomeScore + context.getString(R.string.vs) + match.intAwayScore
            containerView.setOnClickListener{listener(match)}
        }
    }
}