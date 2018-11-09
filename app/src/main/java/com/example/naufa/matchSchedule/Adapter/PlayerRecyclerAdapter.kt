package com.example.naufa.matchSchedule.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.naufa.matchSchedule.Database.FavoriteMatch
import com.example.naufa.matchSchedule.Entity.Player
import com.example.naufa.matchSchedule.R
import kotlinx.android.synthetic.main.card_match.view.*
import kotlinx.android.synthetic.main.card_player.view.*

class PlayerRecyclerAdapter(private val context : Context, private val players : List<Player>, private val listener : (Player) -> Unit ) :
    RecyclerView.Adapter<PlayerRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_player, parent, false))
    }

    override fun getItemCount(): Int {
        return players.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMatch(players[position], listener, context)
    }

    class ViewHolder (private val containerView: View) : RecyclerView.ViewHolder(containerView){

        fun bindMatch(player : Player, listener : (Player) -> Unit, context: Context){

            Glide.with(context).load(player.strCutout).into(containerView.img_player)
            containerView.name_player.text = player.strPlayer
            containerView.role_player.text = player.strPosition
            containerView.setOnClickListener { listener(player) }

        }
    }
}