package com.beloushkin.fooddataviewer.foodlist


import com.beloushkin.fooddataviewer.model.Product
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class FoodListUpdateTest {

    private lateinit var updateSpec: UpdateSpec<FoodListModel, FoodListEvent, FoodListEffect>

    @Before
    fun before() {
        updateSpec = UpdateSpec(::foodListUpdate)
    }

    @Test
    fun initial_observeProductsDispatched() {
        val model = FoodListModel()
        updateSpec
            .given(model)
            .whenEvent(Initial)
            .then(
                assertThatNext<FoodListModel,FoodListEffect>(
                    hasNoModel(),
                    hasEffects(ObserveProducts)
                )
            )
    }

    @Test
    fun addButtonClicked_NavigateToScannerDispatched() {
        val model = FoodListModel()
        updateSpec
            .given(model)
            .whenEvent(AddButtonClicked)
            .then(
                assertThatNext<FoodListModel,FoodListEffect>(
                    hasNoModel(),
                    hasEffects(NavigateToScanner)
                )
            )
    }

    @Test
    fun productsLoaded_products() {
        val model = FoodListModel()
        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brands",
            imageUrl = "www.some.url",
            ingridients = "lots of sugar",
            nutriments = null
        )
        val products = listOf(product, product, product)
        updateSpec
            .given(model)
            .whenEvent(ProductsLoaded(products))
            .then(
                assertThatNext<FoodListModel,FoodListEffect>(
                    hasModel(model.copy(products)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun productClicked_navigateToFoodDetailsDispatched() {
        val model = FoodListModel()
        val id = "Somebarcode"
        updateSpec
            .given(model)
            .whenEvent(ProductClicked(id))
            .then(
                assertThatNext<FoodListModel,FoodListEffect>(
                    hasNoModel(),
                    hasEffects(NavigateToFoodDetails(id))
                )
            )
    }


}


































