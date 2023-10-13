package com.jlndev.listaderepositriosgit

import android.view.View
import androidx.test.core.app.ActivityScenario.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jlndev.listaderepositriosgit.view.home.adapter.GitRepositoriesAdapter
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @JvmField
    @Rule
        var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun clickItemAdapter() {
        onView(isRoot()).perform(waitFor(2000))
        clickItemAtPosition(1)
    }

    private fun clickItemAtPosition(position: Int) {
        onView(withId(R.id.recyclerGitRepositoriesView))
            .perform(RecyclerViewActions.scrollToPosition<GitRepositoriesAdapter.RepositoryItemViewHolder>(position))
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.recyclerGitRepositoriesView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GitRepositoriesAdapter.RepositoryItemViewHolder>(position, clickItemWithId(R.id.itemRepositoryView)))
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    private fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }
}