package com.example.bookstore.core.retrofit

import com.squareup.moshi.Json

data class ImageLinks(
    @Json(name = "thumbnail") val thumbnail: String
)