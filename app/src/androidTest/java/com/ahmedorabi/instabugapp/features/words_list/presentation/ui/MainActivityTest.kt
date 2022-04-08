package com.ahmedorabi.instabugapp.features.words_list.presentation.ui

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedorabi.instabugapp.EspressoIdlingResourceRule
import com.ahmedorabi.instabugapp.R
import com.ahmedorabi.instabugapp.RecyclerViewItemCountAssertion.Companion.withItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)

class MainActivityTest {

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun isWordsListScreenDisplayed() {
        onView(withId(R.id.wordListScreen))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun isRecyclerViewDisplayed() {
        onView(withId(R.id.recycler_view_main))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_search_view() {

        onView(withId(R.id.search))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
            typeText("instabug"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.recycler_view_main)).check(withItemCount(1))

    }


}