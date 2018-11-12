package com.example.naufal.football.Fragment.DetailTeamFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naufal.football.Adapter.FragmentDetailTeamAdapter.Companion.KEY_TEAM
import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.Presenter.TeamPresenter
import com.example.naufal.football.Entity.Team
import com.example.naufal.football.Api.ModelView.TeamListView
import com.example.naufal.football.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_overview.*


class OverviewFragment : Fragment(), TeamListView {

    private lateinit var idTeam: String
    private lateinit var teamPresenter: TeamPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val bindData = arguments
        idTeam = bindData?.getString(KEY_TEAM) ?: "idTeam"

        val request = ApiRepository()
        val gson = Gson()

        teamPresenter = TeamPresenter(this, request, gson)
        teamPresenter.geDetailTeam(idTeam)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun isLoading() {
       progress_circular.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progress_circular.visibility = View.INVISIBLE
    }

    override fun showTeam(data: List<Team>) {

        team_desc_detail.text = data[0].teamDescription

    }


}