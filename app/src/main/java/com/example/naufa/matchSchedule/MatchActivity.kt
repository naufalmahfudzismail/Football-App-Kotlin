package com.example.naufa.matchSchedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.naufa.matchSchedule.Adapter.FragmentMatchAdapter
import com.example.naufa.matchSchedule.R.id.*
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.intentFor

class MatchActivity : AppCompatActivity() {

    lateinit var pagerMatchAdapter: FragmentMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        supportActionBar?.title = getString(R.string.mt_sc)
        setFragment()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                MatchMenu -> {
                    startActivity(
                        intentFor<MatchActivity>()
                    )
                }
                TeamMenu -> {
                    startActivity(
                        intentFor<TeamActivity>()
                    )
                }
                FavoriteMenu -> {
                    startActivity(
                        intentFor<FavoriteActivity>()
                    )
                }
            }
            true
        }

        bottom_navigation.menu.findItem(R.id.MatchMenu).isChecked = true
    }


    private fun setFragment() {

        pagerMatchAdapter = FragmentMatchAdapter(supportFragmentManager)
        viewPager.adapter = pagerMatchAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}
