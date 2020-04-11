package com.beloushkin.fooddataviewer.foodlist

import com.beloushkin.fooddataviewer.model.Product

data class FoodListModel(
    val products: List<Product> = listOf()
)