package com.beloushkin.fooddataviewer.scan

import com.beloushkin.fooddataviewer.model.Product


data class ScanModel(
    val activity: Boolean = false,
    val processBarcodeResult: ProcessBarcodeResult = ProcessBarcodeResult.Empty
)

sealed class ProcessBarcodeResult {
    object Empty: ProcessBarcodeResult()
    object Error: ProcessBarcodeResult()
    data class ProductLoaded(val product: Product): ProcessBarcodeResult()
}