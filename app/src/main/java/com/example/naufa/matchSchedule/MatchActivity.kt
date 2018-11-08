package com.example.naufa.matchSchedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.naufa.matchSchedule.Adapter.FragmentMatchAdapter
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.intentFor

class MatchActivity : AppCompatActivity() {

    lateinit var pagerMatchAdapter: FragmentMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        setFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_fav ->{
                startActivity(intentFor<FavoriteActivity>())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setFragment() {

        pagerMatchAdapter = FragmentMatchAdapter(supportFragmentManager)
        viewPager.adapter = pagerMatchAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}
