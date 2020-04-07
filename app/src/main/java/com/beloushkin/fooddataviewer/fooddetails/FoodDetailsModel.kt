package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.model.Product

data class FoodDetailsModel(val activity: Boolean = false, val product: Product? = null, val error: Boolean = false)
