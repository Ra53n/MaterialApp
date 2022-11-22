import android.content.Context
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.materialapp.R
import com.example.materialapp.ui.view.fragment.MainFragment
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    private lateinit var scenario: FragmentScenario<MainFragment>

    private lateinit var context: Context

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_EARTH)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun wikiSearch_Test() {
        val wikiIcon = onView(withId(R.id.wikiIcon))
        wikiIcon.check(matches(isDisplayed()))
        val searchInputLayout = onView(withId(R.id.searchTextInputLayout))
        searchInputLayout.check(matches(not(isDisplayed())))
        wikiIcon.perform(ViewActions.swipeRight())
        searchInputLayout.check(matches(isDisplayed()))
    }

    @Test
    fun chipsClicks_Test() {
        val todayChip = onView(withId(R.id.chipToday))
        val yesterdayChip = onView(withId(R.id.chipYesterday))
        todayChip.check(matches(isChecked()))
        yesterdayChip.check(matches(not(isChecked())))
        yesterdayChip.perform(ViewActions.click())
        todayChip.check(matches(not(isChecked())))
        yesterdayChip.check(matches(isChecked()))
    }

    @Test
    fun imagePOD_Test() {
        val image = onView(withId(R.id.image))
        val imageTitle = onView(withId(R.id.title))
        onView(isRoot()).perform(delay())
        imageTitle.check(matches(isDisplayed()))
        image.perform(ViewActions.click())
        onView(isRoot()).perform(delay())
        imageTitle.check(matches(not(isDisplayed())))
    }

    @Test
    fun visibilityOfViews_Test() {
        val wikiIcon = onView(withId(R.id.wikiIcon))
        val todayChip = onView(withId(R.id.chipToday))
        val yesterdayChip = onView(withId(R.id.chipYesterday))
        val image = onView(withId(R.id.image))
        val imageTitle = onView(withId(R.id.title))
        val fab = onView(withId(R.id.fab))
        onView(isRoot()).perform(delay())
        wikiIcon.check(matches(isDisplayed()))
        todayChip.check(matches(isDisplayed()))
        yesterdayChip.check(matches(isDisplayed()))
        image.check(matches(isDisplayed()))
        imageTitle.check(matches(isDisplayed()))
        fab.check(matches(isDisplayed()))
    }

    @Test
    fun searchLayout_test() {
        val wikiIcon = onView(withId(R.id.wikiIcon))
        wikiIcon.perform(ViewActions.swipeRight())
        val searchEditText = onView(withId(R.id.searchEditText))
        searchEditText.perform(ViewActions.click())
        searchEditText.perform(ViewActions.typeText("some text"))
        searchEditText.check(matches(withText("some text")))
    }


    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}