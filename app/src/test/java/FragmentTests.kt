import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import com.example.materialapp.R
import com.example.materialapp.ui.view.fragment.MainFragment
import com.google.android.material.internal.CheckableImageButton
import kotlinx.android.synthetic.main.main_fragment.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MainFragmentTest {

    private lateinit var scenario: FragmentScenario<MainFragment>

    private lateinit var context: Context

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_EARTH)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun mainFragment_Test() {
        scenario.onFragment {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun changeChip_Test() {
        scenario.onFragment {
            val chipToday = it.chipToday
            val chipYesterday = it.chipYesterday
            Assert.assertEquals(true, chipToday.isChecked)
            Assert.assertEquals(false, chipYesterday.isChecked)
            chipYesterday.performClick()
            Assert.assertEquals(true, chipYesterday.isChecked)
            Assert.assertEquals(false, chipToday.isChecked)
        }
    }

    @Test
    fun titleClick_Test() {
        scenario.onFragment {
            val someText = "some text"
            it.title.text = someText
            Assert.assertEquals("", it.searchEditText.text.toString())
            it.title.performClick()
            Assert.assertEquals(someText, it.searchEditText.text.toString())
        }
    }

    @Test
    fun incorrectSearch_Test() {
        scenario.onFragment {
            it.searchTextInputLayout
                .findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_end_icon)
                .callOnClick()
            ShadowLooper.idleMainLooper()
            Assert.assertEquals(
                ShadowToast.getTextOfLatestToast(),
                context.getString(R.string.incorrect_request)
            )
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}