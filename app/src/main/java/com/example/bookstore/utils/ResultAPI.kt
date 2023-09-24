package com.example.bookstore.utils

sealed class ResultAPI<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : ResultAPI<T>(data, null)
    class Error<T>(message: String) : ResultAPI<T>(null, message)
}
