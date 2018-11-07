package com.example.naufa.matchSchedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        activityRule.activity.pagerAdapter.getItemPosition(0)
    }

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(R.id.match_last_rcy))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        onView(withId(R.id.match_last_rcy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.match_last_rcy)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(3000)

        onView(withId(R.id.add_to_favorite)).perform(click())
        pressBack()
        Thread.sleep(5000)

        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withId(R.id.match_next_rcy))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        onView(withId(R.id.match_next_rcy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.match_next_rcy)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(3000)

        onView(withId(R.id.add_to_favorite)).perform(click())
        pressBack()
        Thread.sleep(8000)

        onView(withId(R.id.action_fav)).perform(click())
        pressBack()

        /*
            Scenario Testing
            1) go to Main Activity
            2) go to Fragment 1 ( Last Match fragment)
            3) display recycler view in Fragment 1
            4) Scroll Recycler view and click item number 10
            5) Open up it's detail activity, and click favorite button
            6) back to Main Activity
            7) Swipe into Fragment number 2 (Next Match Fragment)
            8) Scroll Recycler view and click item number 10
            9) Open up it detail activity, and click favorite button
            10) Back to Main Activity
            11) Click favorite button
            12) go to Favorite activity
            13) back to Main Activity
         */
    }


}