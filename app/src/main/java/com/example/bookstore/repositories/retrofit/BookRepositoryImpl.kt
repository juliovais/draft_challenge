package com.example.bookstore.repositories.retrofit

import com.example.bookstore.core.retrofit.Volume
import com.example.bookstore.core.retrofit.VolumeResponse
import com.example.bookstore.interfaces.retrofit.BookRepository
import com.example.bookstore.interfaces.retrofit.BooksAPI
import com.example.bookstore.utils.ResultAPI
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val api: BooksAPI) : BookRepository {

    override suspend fun getVolumes(
        q: String,
        maxResults: Int,
        startIndex: Int
    ): ResultAPI<VolumeResponse> {

        return try {
            val response = api.getVolumes(q, maxResults, startIndex)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                ResultAPI.Success(result)
            } else {
                ResultAPI.Error(response.message())
            }
        } catch (e: Exception) {
            ResultAPI.Error(e.message ?: "Exception occurred")
        }
    }
}