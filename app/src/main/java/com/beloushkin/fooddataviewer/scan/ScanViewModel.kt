package com.beloushkin.fooddataviewer.scan

import com.beloushkin.fooddataviewer.MobiusVM
import com.beloushkin.fooddataviewer.scan.handlers.ProcessBarcodeHandler
import com.beloushkin.fooddataviewer.scan.handlers.ProcessFrameHandler
import com.beloushkin.fooddataviewer.utils.Navigator
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

fun scanUpdate(
    model: ScanModel,
    event: ScanEvent
): Next<ScanModel, ScanEffect> {
    return when(event) {
        is Captured -> Next.dispatch(setOf(ProcessCameraFrame(event.frame)))
        is Detected -> if (!model.activity){
            next<ScanModel, ScanEffect> (model.copy(activity = true),
                setOf(ProcessBarcode(event.barcode))
            )
        } else {
            noChange<ScanModel, ScanEffect>()
        }
        is ProductLoaded -> next(
            model.copy(activity = false,
                       processBarcodeResult = ProcessBarcodeResult.ProductLoaded(event.product)
            )
        )
        is BarcodeError -> next(
            model.copy(
                activity = false,
                processBarcodeResult = ProcessBarcodeResult.Error
            )
        )
        ProductInfoClicked -> if (model.processBarcodeResult is ProcessBarcodeResult.ProductLoaded){
            dispatch<ScanModel, ScanEffect>(
                setOf(NavigateToFoodDetails(model.processBarcodeResult.product.id))
            )
        } else {
            noChange<ScanModel, ScanEffect>()
        }
    }
}

class ScanViewModel @Inject constructor(
    processCameraFrameHandler: ProcessFrameHandler,
    processBarcodeHandler: ProcessBarcodeHandler,
    navigator: Navigator
)
    : MobiusVM<ScanModel, ScanEvent, ScanEffect>(
    "ScanViewModel",
    Update(::scanUpdate),
    ScanModel(),
    RxMobius.subtypeEffectHandler<ScanEffect, ScanEvent>()
        .addTransformer(ProcessCameraFrame::class.java, processCameraFrameHandler)
        .addTransformer(ProcessBarcode::class.java, processBarcodeHandler)
        .addConsumer(NavigateToFoodDetails::class.java) { effect ->
            navigator.to(ScanFragmentDirections.foodDetails(effect.barcode))
        }
        .build()
)
