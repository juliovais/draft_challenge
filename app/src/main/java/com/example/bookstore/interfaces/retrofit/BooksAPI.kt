package com.example.bookstore.interfaces.retrofit

import com.example.bookstore.core.retrofit.VolumeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksAPI {

    @GET("volumes")
    suspend fun getVolumes(
        @Query("q") q: String,
        @Query("maxResults") maxResults: Int,
        @Query("startIndex") startIndex: Int
    ): Response<VolumeResponse>
}