package com.beloushkin.fooddataviewer.fooddetails

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.component
import com.beloushkin.fooddataviewer.di.TestComponent
import com.beloushkin.fooddataviewer.utils.TestIdlingResource
import com.beloushkin.fooddataviewer.utils.readJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

class FoodDetailsFragmentTest {

    lateinit var json: String
    private val mockWebServer = MockWebServer()

    @After
    fun afterTest() {
        mockWebServer.close()
    }

    @Test
    fun viewsTest() {
        val barcode = "8888002076009"
        val fragmentArgs = Bundle().apply {
            putString("barcode", barcode)
        }
        var idlingResource: TestIdlingResource? = null
        var resources: Resources? = null
        var context: Context? = null
        val scenario = launchFragmentInContainer<FoodDetailsFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar,
            fragmentArgs = fragmentArgs
        )
        scenario.onFragment {fragment ->
            resources = fragment.resources
            context = fragment.requireContext()
//            idlingResource = ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
//            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)
//            idlingResource!!.increment()
        }

        val mockResponse = MockResponse()


        json = readJson(context!!, "productresponse.json")
        mockResponse.setBody(json)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.productNameView)).check(matches(withText("Coke Classic")))
        //        onView(withId(R.id.brandNameView)).check(matches(withText("Coca-Cola")))
        //        onView(withId(R.id.barcodeView)).check(matches(withText(barcode)))
        //        onView(withId(R.id.energyValueView))
        //            .perform(scrollTo())
        //            .check(
        //                matches(
        //                    withText(
        //                        resources!!.getString(
        //                            R.string.food_details_energy_value,
        //                            176
        //
        //                        )
        //                    )
        //                )
        //            )
        //        onView(withId(R.id.carbsValueView)).check(
        //            matches(
        //                withText(
        //                    resources!!.getString(
        //                        R.string.food_details_macro_value,
        //                        10.6
        //
        //                    )
        //                )
        //            )
        //        )
        //        onView(withId(R.id.fatValueView)).check(
        //            matches(
        //                withText(
        //                    resources!!.getString(
        //                        R.string.food_details_macro_value,
        //                        0.0
        //
        //                    )
        //                )
        //            )
        //        )
        //        onView(withId(R.id.proteinValueView)).check(
        //            matches(
        //                withText(
        //                    resources!!.getString(
        //                        R.string.food_details_macro_value,
        //                        0.0
        //
        //                    )
        //                )
        //            )
        //        )
        //        onView(withId(R.id.ingridientsText)).check(
        //            matches(
        //                withText(
        //                    resources!!.getString(
        //                        R.string.food_details_ingridients,
        //                        "CARBONATED WATER, HIGH FRUCTOSE CORN SYRUP, SUCROSE, CARAMEL COLOUR, PHOSPHORIC ACID, FLAVOURINGS, CAFFEINE"
        //                    )
        //                )
        //            )
        //        )
        //onView(withId(R.id.actionButton)).check(matches(withText(resources!!.getString(R.string.food_details_save))))


        //IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
    }

}