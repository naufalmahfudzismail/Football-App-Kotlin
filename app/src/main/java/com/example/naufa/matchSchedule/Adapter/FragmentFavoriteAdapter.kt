package com.example.naufa.matchSchedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.naufa.matchSchedule.Database.FavoriteMatch
import com.example.naufa.matchSchedule.Fragment.FavoriteFragment.FavoriteMatchFragment
import com.example.naufa.matchSchedule.Fragment.FavoriteFragment.FavoriteTeamFragment
import com.example.naufa.matchSchedule.Fragment.MatchFragment.LastMatchFragment
import com.example.naufa.matchSchedule.Fragment.MatchFragment.NextMatchFragment

class FragmentFavoriteAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    private val count  = 2

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteMatchFragment()
            1 -> fragment = FavoriteTeamFragment()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0){
            "Favorite Match"
        } else{
            "Favorite Team"
        }
    }
    override fun getCount(): Int {
       return count
    }

}