package com.example.bookstore.core.retrofit

import com.squareup.moshi.Json

data class Volume(
    @Json(name = "title") val title: String,
    @Json(name = "authors") val authors: List<String>,
    @Json(name = "description") val description: String,
    @Json(name = "imageLinks") val imageLinks: ImageLinks?,
    @Json(name = "saleInfo") val saleInfo: SaleInfo?,
)