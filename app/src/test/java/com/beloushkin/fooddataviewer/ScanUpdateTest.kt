package com.beloushkin.fooddataviewer

import com.beloushkin.fooddataviewer.model.Product
import com.beloushkin.fooddataviewer.scan.*
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.preview.Frame
import org.junit.Before
import org.junit.Test

class ScanUpdateTest {

    private lateinit var updateSpec: UpdateSpec<ScanModel, ScanEvent, ScanEffect>

    @Before
    fun before() {
        updateSpec = UpdateSpec(::scanUpdate)
    }

    @Test
    fun capturedEvent_processFrameDispatched() {
        val model = ScanModel()
        val frame = Frame(size = Resolution(width = 100, height = 100),
                          image = ByteArray(100),
                          rotation = 100)
        updateSpec
            .given(model)
            .whenEvent(Captured(frame))
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(ProcessCameraFrame(frame) as ScanEffect)
                )
            )
    }

    @Test
    fun detectedEvent_activity_processBarcodeDispatched() {
        val model = ScanModel()
        val barcode = "Some barcode"
        updateSpec
            .given(model)
            .whenEvent(Detected(barcode))
            .then(
                assertThatNext<ScanModel, ScanEffect>(
                    hasModel(model.copy(activity = true)),
                    hasEffects(ProcessBarcode(barcode))
                )
            )
    }

    @Test
    fun detectedEvent_noChange() {
        val model = ScanModel(activity = true)
        val barcode = "Some barcode"
        updateSpec
            .given(model)
            .whenEvent(Detected(barcode))
            .then(
                assertThatNext<ScanModel, ScanEffect>(
                    hasNothing()
                )
            )
    }

    @Test
    fun barcodeErrorEvent_activityFalseProcessBarcodeResultError() {
        val model = ScanModel(activity = true)
        updateSpec
            .given(model)
            .whenEvent(BarcodeError)
            .then(
                assertThatNext<ScanModel, ScanEffect>(
                    hasModel(
                        model.copy(
                            activity = false,
                            processBarcodeResult = ProcessBarcodeResult.Error
                        )
                    ),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun productLoadedEvent_activityFalseProductLoaded() {
        val model = ScanModel(activity = true)
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
                assertThatNext<ScanModel, ScanEffect>(
                    hasModel(
                        model.copy(
                            activity = false,
                            processBarcodeResult = ProcessBarcodeResult.ProductLoaded(product)
                        )
                    ),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun productInfoClickedEvent_navigateToFoodDetailsDispatched() {
        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brands",
            imageUrl = "www.some.url",
            ingridients = "lots of sugar",
            nutriments = null
        )
        val model = ScanModel(
            activity = true,
            processBarcodeResult = ProcessBarcodeResult.ProductLoaded(product)
        )
        updateSpec
            .given(model)
            .whenEvent(ProductInfoClicked)
            .then(
                assertThatNext<ScanModel, ScanEffect>(
                    hasNoModel(),
                    hasEffects(NavigateToFoodDetails(product.id))
                )
            )

    }

    @Test
    fun productInfoClickedNoChange() {
        val model = ScanModel()
        updateSpec
            .given(model)
            .whenEvent(ProductInfoClicked)
            .then(
                assertThatNext<ScanModel, ScanEffect>(
                    hasNothing()
                )
            )

    }
}