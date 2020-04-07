package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.model.Product

sealed class FoodDetailsEvent

data class Initial(val barcode: String): FoodDetailsEvent()

object ActionButtonClicked: FoodDetailsEvent()

data class ProductLoaded(val product: Product): FoodDetailsEvent()

object ErrorLoadingProduct: FoodDetailsEvent()
