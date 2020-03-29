package com.beloushkin.fooddataviewer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
    val product: ProductDto
)
