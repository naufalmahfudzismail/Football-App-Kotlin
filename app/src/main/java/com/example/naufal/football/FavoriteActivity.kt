package com.example.naufal.football

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.naufal.football.Adapter.FragmentFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.intentFor


class FavoriteActivity : AppCompatActivity(){

    lateinit var pagerFavoriteAdapter : FragmentFavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = getString(R.string.favs)
        setFragment()

        fav_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.MatchMenu -> {
                    startActivity(
                        intentFor<MatchActivity>()
                    )
                }
                R.id.TeamMenu -> {
                    startActivity(
                        intentFor<TeamActivity>()
                    )
                }
                R.id.FavoriteMenu -> {
                    startActivity(
                        intentFor<FavoriteActivity>()
                    )
                }

            }
            true
        }
        fav_bottom_navigation.menu.findItem(R.id.FavoriteMenu).isChecked = true

    }

    private fun setFragment() {

        pagerFavoriteAdapter = FragmentFavoriteAdapter(supportFragmentManager)
        fav_viewPager.adapter = pagerFavoriteAdapter
        fav_tabLayout.setupWithViewPager(fav_viewPager)

    }

}
