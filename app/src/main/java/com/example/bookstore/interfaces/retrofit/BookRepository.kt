package com.example.bookstore.interfaces.retrofit

import com.example.bookstore.core.retrofit.VolumeResponse
import com.example.bookstore.utils.ResultAPI

interface BookRepository {

    suspend fun getVolumes(
        q: String,
        maxResults: Int,
        startIndex: Int
    ): ResultAPI<VolumeResponse>
}