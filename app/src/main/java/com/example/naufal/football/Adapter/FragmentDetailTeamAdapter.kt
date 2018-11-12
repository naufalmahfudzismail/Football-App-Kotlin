package com.example.naufal.football.Adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.naufal.football.Fragment.DetailTeamFragment.OverviewFragment
import com.example.naufal.football.Fragment.DetailTeamFragment.PlayerFragment

class FragmentDetailTeamAdapter(private val idTeam: String, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    private val count  = 2

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = newInstanceOverview(idTeam)
            1 -> fragment = newInstancePlayer(idTeam)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0){
            "Overview"
        } else{
            "Players"
        }
    }
    override fun getCount(): Int {
       return count
    }

    companion object {

        const val KEY_TEAM = "KEY_TEAM"
        const val KEY_TEAM_2 = "KEY_TEAM_2"

        fun newInstanceOverview(id: String): OverviewFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM, id)

            val overviewFragment = OverviewFragment()
           overviewFragment.arguments = bindData
            return overviewFragment
        }

        fun newInstancePlayer(id: String): PlayerFragment {
            val bindData = Bundle()
            bindData.putString(KEY_TEAM_2, id)

            val playerFragment = PlayerFragment()
            playerFragment.arguments = bindData
            return playerFragment
        }
    }

}