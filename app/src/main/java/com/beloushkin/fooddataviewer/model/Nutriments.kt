package com.beloushkin.fooddataviewer.model

data class Nutriments (
    val energy: Int,
    val salt: Double?,
    val carbohydrates: Double,
    val fiber: Double?,
    val sugars: Double?,
    val proteins: Double,
    val fat: Double
)