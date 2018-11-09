package com.example.naufa.matchSchedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.naufa.matchSchedule.Fragment.MatchFragment.LastMatchFragment
import com.example.naufa.matchSchedule.Fragment.MatchFragment.NextMatchFragment
import com.example.naufa.matchSchedule.Fragment.MatchFragment.SearchMatchFragment

class FragmentMatchAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val count = 3

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = LastMatchFragment()
            1 -> fragment = NextMatchFragment()
            2 -> fragment = SearchMatchFragment()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Last Match"
            1 -> "Next Match"
            else -> "Search Match"
        }
    }

    override fun getCount(): Int {
        return count
    }

}