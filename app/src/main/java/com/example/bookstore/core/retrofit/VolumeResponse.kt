package com.example.bookstore.core.retrofit

import com.squareup.moshi.Json

data class VolumeResponse(@Json(name = "items") val items: List<Item>)
