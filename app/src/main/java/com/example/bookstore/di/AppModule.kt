package com.example.bookstore.di

import com.example.bookstore.interfaces.retrofit.BookRepository
import com.example.bookstore.interfaces.retrofit.BooksAPI
import com.example.bookstore.repositories.retrofit.BookRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideBookAPI(moshi: Moshi): BooksAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(BooksAPI::class.java)

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksAPI): BookRepository = BookRepositoryImpl(api)
}