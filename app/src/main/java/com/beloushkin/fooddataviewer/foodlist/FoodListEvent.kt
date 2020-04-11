package com.beloushkin.fooddataviewer.foodlist

import com.beloushkin.fooddataviewer.model.Product

sealed class FoodListEvent

object Initial: FoodListEvent()

data class ProductsLoaded(val products: List<Product>): FoodListEvent()

object AddButtonClicked: FoodListEvent()

data class ProductClicked(val barcode: String): FoodListEvent()