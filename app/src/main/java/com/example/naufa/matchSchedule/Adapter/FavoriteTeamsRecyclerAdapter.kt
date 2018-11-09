package com.example.naufa.matchSchedule.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.naufa.matchSchedule.Database.FavoriteTeam
import com.example.naufa.matchSchedule.R
import kotlinx.android.synthetic.main.card_team.view.*

class FavoriteTeamsRecyclerAdapter(private val context : Context, private val teams : List<FavoriteTeam>, private val listener : (FavoriteTeam) -> Unit ) :
    RecyclerView.Adapter<FavoriteTeamsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_team, parent, false))
    }

    override fun getItemCount(): Int {
        return teams.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMatch(teams[position], listener, context)
    }

    class ViewHolder (private val containerView: View) : RecyclerView.ViewHolder(containerView){

        fun bindMatch(team : FavoriteTeam, listener : (FavoriteTeam) -> Unit, context: Context){
            containerView.name_team.text =  team.teamName
            team.teamBadge?.let{ Glide.with(context).load(it).into(containerView.img_team)}
            containerView.setOnClickListener{listener(team)}
        }
    }
}