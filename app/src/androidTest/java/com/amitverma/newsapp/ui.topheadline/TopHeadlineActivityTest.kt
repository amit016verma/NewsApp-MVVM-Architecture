package com.amitverma.newsapp.ui.topheadline


import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.amitverma.newsapp.TestComponentRule
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.RVMatcher.atPositionOnView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import com.amitverma.newsapp.R


class TopHeadlineActivityTest {

    private val component =
        TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val chain = RuleChain.outerRule(component)


    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        TopHeadlineActivity::class.java
    )

    @Before
    open fun intentsInit() {
        // initialize Espresso Intents capturing
        Intents.init()
    }

    @After
    fun intentsTeardown() {
        // release Espresso Intents capturing
        Intents.release()
    }

    @Test
    fun topHeadlineAvailable_shouldDisplay() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPositionOnView(
                    0, withText("title1"), R.id.textViewTitle
                )
            )
        )
        onView(withId(R.id.recyclerView)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(4)
        ).check(matches(atPositionOnView(4, withText("title5"), R.id.textViewTitle)))
    }

    @Test
    fun whenInternetAndDBDataBothNotAvailable_shouldDisplayErrorScreen() {
        val dao = component.testComponent!!.getNewsAppDatabase().topHeadlinesDao()
        dao.clearTopHeadlinesArticles(AppConstant.COUNTRY)
        val networkHelper = component.testComponent!!.getNetworkHelper()
        networkHelper.setStatus(false)
        onView(withId(R.id.includeLayout)).check(matches(isDisplayed()))
    }
}