package com.example.naufal.football

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.PlayerDetailPresenter
import com.example.naufal.football.Entity.PlayerDetail
import com.example.naufal.football.Api.ModelView.PlayerDetailView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_player.*
import org.jetbrains.anko.intentFor

class DetailPlayerActivity : AppCompatActivity(), PlayerDetailView {


    private lateinit var playerDetailPresenter: PlayerDetailPresenter
    private lateinit var idPlayer: String
    private lateinit var idTeam : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.title = getString(R.string.title_player_activity)
        idPlayer = intent.getStringExtra("idPlayer")
        idTeam = intent.getStringExtra("idTeam")
        val request = ApiRepository()
        val gson = Gson()

        playerDetailPresenter = PlayerDetailPresenter(this, request, gson)
        playerDetailPresenter.getPlayerList(idPlayer)
    }


    override fun isLoading() {
        progress_bar_player.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_bar_player.visibility = View.INVISIBLE
    }

    override fun showPlayerDetail(data: List<PlayerDetail>) {

        data[0].strFanart1?.let { Glide.with(this).load(it).into(player_img_detail) }
        player_role_detail.text = data[0].strPosition
        player_forward_detail.text = data[0].strDescriptionEN
        player_weight_detail.text = data[0].strWeight
        player_height_detail.text = data[0].strHeight
        supportActionBar?.title = data[0].strPlayer

    }
}
