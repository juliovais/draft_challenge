package com.example.bookstore.core.retrofit

import com.squareup.moshi.Json

data class Item(@Json(name = "volumeInfo") val volumeInfo: Volume)
