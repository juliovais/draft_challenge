package com.example.bookstore.core.retrofit

import com.squareup.moshi.Json

data class SaleInfo(
    @Json(name = "buyLink") val buyLink: String
)