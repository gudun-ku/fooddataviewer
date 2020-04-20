package com.beloushkin.fooddataviewer.foodlist


import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.beloushkin.fooddataviewer.AndroidTestApplication
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.model.dto.NutrimentsDto
import com.beloushkin.fooddataviewer.model.dto.ProductDto
import com.beloushkin.fooddataviewer.utils.withRecyclerView
import org.junit.Test

class FoodListFragmentTest {

    private val id = "1234"
    private val name = "name"
    private val brands = "brands"
    private val imageUrl = "imageUrl"
    private val ingredients = "ingredients"
    private val energy = 10
    private val salt = 11.0
    private val carbs = 12.0
    private val fiber = 13.0
    private val sugars = 14.0
    private val proteins = 15.0
    private val fat = 16.0
    private val productDto = ProductDto(
        id,
        name,
        brands,
        imageUrl,
        ingredients,
        NutrimentsDto(energy, salt, carbs, fiber, sugars, proteins, fat)
    )

    @Test
    fun views() {
        val scenario =
            launchFragmentInContainer<FoodListFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
            )
        var resources: Resources? = null
        scenario.onFragment { fragment ->
            resources = fragment.resources
            (fragment.activity!!.applicationContext as AndroidTestApplication).productDtosSubject.onNext(
                listOf(
                    productDto
                )
            )
        }
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.productNameView))
            .check(matches(withText(name)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.brandNameView))
            .check(matches(withText(brands)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.energyValueView))
            .check(
                matches(
                    withText(
                        resources!!.getString(
                            R.string.food_list_energy_value, energy

                        )
                    )
                )
            )
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.carbsValueView))
            .check(
                matches(
                    withText(
                        resources!!.getString(
                            R.string.food_list_macro_value, carbs
                        )
                    )
                )
            )
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.fatValueView))
        matches(
            withText(
                resources!!.getString(
                    R.string.food_list_macro_value, fat

                )
            )
        )
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.productNameView))
        matches(
            withText(
                resources!!.getString(
                    R.string.food_list_macro_value, proteins

                )
            )
        )
    }
}