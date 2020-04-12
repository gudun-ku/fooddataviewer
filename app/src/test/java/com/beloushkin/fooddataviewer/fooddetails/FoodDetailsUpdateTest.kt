package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.model.Product
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class FoodDetailsUpdateTest {

    private lateinit var updateSpec: UpdateSpec<FoodDetailsModel, FoodDetailsEvent, FoodDetailsEffect>

    @Before
    fun before() {
        updateSpec = UpdateSpec(::foodDetailsUpdate)
    }

    @Test
    fun initialEvent_activityTrue_loadProductDispatched() {
        val model = FoodDetailsModel()
        val barcode = "111"
        updateSpec
            .given(model)
            .whenEvent(Initial(barcode))
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity = true)),
                    hasEffects(LoadProduct(barcode))
                )
            )
    }

    @Test
    fun productLoaded_activityFalse_Product() {
        val model = FoodDetailsModel(activity = true)
        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brands",
            imageUrl = "www.some.url",
            ingridients = "lots of sugar",
            nutriments = null
        )
        updateSpec
            .given(model)
            .whenEvent(ProductLoaded(product))
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity = false, product = product)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun errorLoadingProduct_activityFalse_Error() {
        val model = FoodDetailsModel(activity = true)
        updateSpec
            .given(model)
            .whenEvent(ErrorLoadingProduct)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity = false, error = true)),
                    hasNoEffects()
                )
            )

    }

    @Test
    fun actionButtonClicked_deleteProductDispatched() {
        val product = Product(
            id = "someId",
            saved = true,
            name = "product",
            brands = "brands",
            imageUrl = "www.some.url",
            ingridients = "lots of sugar",
            nutriments = null
        )
        val model = FoodDetailsModel(product = product)

        updateSpec
            .given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(product = model.product!!.copy(saved = !product.saved))),
                    hasEffects(DeleteProduct(product.id))
                )
            )
    }

    @Test
    fun actionButtonClicked_saveProductDispatched() {
        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brands",
            imageUrl = "www.some.url",
            ingridients = "lots of sugar",
            nutriments = null
        )
        val model = FoodDetailsModel(product = product)

        updateSpec
            .given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(product = model.product!!.copy(saved = !product.saved))),
                    hasEffects(SaveProduct(product))
                )
            )
    }

    @Test
    fun actionButtonClicked_noChange() {
        val model = FoodDetailsModel()
        updateSpec
            .given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasNothing()
                )
            )
    }
}