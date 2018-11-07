package com.example.naufa.matchSchedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.naufa.matchSchedule.Adapter.FragmentAdapter
import com.example.naufa.matchSchedule.Fragment.LastMatchFragment
import com.example.naufa.matchSchedule.Fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    lateinit var pagerAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        pagerAdapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}
