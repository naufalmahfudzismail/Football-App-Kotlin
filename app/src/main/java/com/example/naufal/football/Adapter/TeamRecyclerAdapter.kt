package com.example.naufal.football.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.naufal.football.Entity.Team
import com.example.naufal.football.R
import kotlinx.android.synthetic.main.card_team.view.*

class TeamRecyclerAdapter(private val context : Context, private val teams : List<Team>, private val listener : (Team) -> Unit ) :
    RecyclerView.Adapter<TeamRecyclerAdapter.ViewHolder>() {

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

        fun bindMatch(team : Team, listener :(Team) -> Unit, context: Context){
            containerView.name_team.text =  team.teamName
            team.strTeamBadge?.let{ Glide.with(context).load(it).into(containerView.img_team)}
            containerView.setOnClickListener{listener(team)}
        }
    }
}
