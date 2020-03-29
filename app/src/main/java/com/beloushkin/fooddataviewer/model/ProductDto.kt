package com.beloushkin.fooddataviewer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
class ProductDto (
    val id: String,
    val product_name: String,
    val brands: String,
    val image_url: String,
    val ingridients_text_debug: String
)
