package com.example.ddiff.myfootballclubapi.mvp.ui.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R.id.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 * github : ddiffa
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchOption() {
        onView(withId(action_search)).perform(click())
        onView(withId(search)).perform(click())
        onView(withHint("Search Match")).perform(typeText("a"))
        Thread.sleep(2000)
        onView(withId(rv_search))
                .check(matches(isDisplayed()))
        onView(withId(rv_search)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rv_search)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        pressBack()
    }


    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(2000)
        onView(withId(rv_fragment_next_match))
                .check(matches(isDisplayed()))
        onView(withId(rv_fragment_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_fragment_next_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()
        onView(withId(view_pager)).perform(swipeLeft())
        Thread.sleep(2000)
        //cek recylerview
        onView(withId(rv_fragment_match))
                .check(matches(isDisplayed()))
        onView(withId(rv_fragment_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rv_fragment_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        pressBack()


        //team
        onView(withId(menu_team)).perform(click())
        Thread.sleep(2000)
        onView(withId(rv_team))
                .check(matches(isDisplayed()))

        onView(withId(rv_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(add_to_favorite)).perform(click())
        val tabView = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(withId(tab_layout_team),
                                0),
                        1),
                        isDisplayed()))

        tabView.perform(click())

        onView(withId(rv_player))
                .check(matches(isDisplayed()))
        onView(withId(rv_player)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        pressBack()
        pressBack()
        Thread.sleep(2000)
        onView(withId(search_view)).perform(click())
        onView(withHint("Search Team")).perform(typeText("a"))
        Thread.sleep(2000)
        pressBack()

        onView(withId(menu_fav)).perform(click())
        Thread.sleep(2000)
        onView(withId(rv_match_favorite))
                .check(matches(isDisplayed()))
        onView(withId(rv_match_favorite)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        onView(withId(swipeFav)).perform(ViewActions.swipeDown())
        Thread.sleep(2000)

        onView(withId(view_pager)).perform(swipeLeft())
        Thread.sleep(2000)
        onView(withId(rv_team_favorite))
                .check(matches(isDisplayed()))
        onView(withId(rv_team_favorite)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        onView(withId(swipeFavTeam)).perform(ViewActions.swipeDown())
        Thread.sleep(2000)

    }


    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}