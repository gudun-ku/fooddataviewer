package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.model.Product

sealed class FoodDetailsEffect

data class LoadProduct(val barcode: String): FoodDetailsEffect()

data class DeleteProduct(val barcode: String): FoodDetailsEffect()

data class SaveProduct(val product: Product): FoodDetailsEffect()