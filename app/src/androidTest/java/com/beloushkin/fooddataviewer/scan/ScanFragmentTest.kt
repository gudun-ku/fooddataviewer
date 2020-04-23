package com.beloushkin.fooddataviewer.scan

import android.Manifest
import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.component
import com.beloushkin.fooddataviewer.di.TestComponent
import com.beloushkin.fooddataviewer.utils.TestFrameProcessorOnSubscribe
import com.beloushkin.fooddataviewer.utils.TestIdlingResource
import com.beloushkin.fooddataviewer.utils.readJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test


class ScanFragmentTest {

    lateinit var json: String
    private val mockWebServer = MockWebServer()

    @Rule
    @JvmField
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.CAMERA
    )

    @After
    fun afterTest() {
        mockWebServer.close()
    }

    @Test
    fun succesfulScanResult(){
        val scenario = launchFragmentInContainer<ScanFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
        var idlingResource: TestIdlingResource? = null
        var resources: Resources? = null
        var context: Context? = null

        scenario.onFragment {fragment ->
            resources = fragment.resources
            context = fragment.requireContext()
            idlingResource = ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)
            idlingResource!!.increment()

            (fragment.activity!!.component.frameProcessorOnSubscribe() as TestFrameProcessorOnSubscribe)
                .testFrame = getFrame(fragment.requireContext(), "coca-cola.jpg")

        }

        val mockResponse = MockResponse()


        json = readJson(context!!, "scanfragmenttest.json")
        mockResponse.setBody(json)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.productView)).check(matches(isDisplayed()))
        onView(withId(R.id.productNameView)).check(matches(withText("Coke Classic")))
        onView(withId(R.id.brandNameView)).check(matches(withText("Coca-Cola")))
        onView(withId(R.id.energyValueView)).check(
            matches(
                withText(
                    resources!!.getString(
                        R.string.scan_energy_value, 176

                    )
                )
            )
        )
        onView(withId(R.id.carbsValueView)).check(
            matches(
                withText(
                    resources!!.getString(
                        R.string.scan_macro_value, 10.6

                    )
                )
            )
        )
        onView(withId(R.id.fatValueView)).check(
            matches(
                withText(
                    resources!!.getString(
                        R.string.scan_macro_value, 0.0

                    )
                )
            )
        )
        onView(withId(R.id.proteinValueView)).check(
            matches(
                withText(
                    resources!!.getString(
                        R.string.scan_macro_value, 0.0

                    )
                )
            )
        )



        IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
    }

    @Test
    fun errorScanResult(){
        val scenario = launchFragmentInContainer<ScanFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
        var idlingResource: TestIdlingResource? = null

        scenario.onFragment {fragment ->
            idlingResource = ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)
            idlingResource!!.increment()

            (fragment.activity!!.component.frameProcessorOnSubscribe() as TestFrameProcessorOnSubscribe)
                .testFrame = getFrame(fragment.requireContext(), "coca-cola.jpg")

        }

        val mockResponse = MockResponse()

        mockResponse.setResponseCode(400)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.productView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))


        IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
    }


}