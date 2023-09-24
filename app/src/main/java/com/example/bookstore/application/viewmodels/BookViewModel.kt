package com.example.bookstore.application.viewmodels

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.interfaces.retrofit.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import coil.load

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    fun test(imgView: ImageView) {

        viewModelScope.launch {
            val response = repository.getVolumes("ios", 20, 0)

            val data = response.data
            val imgUrl = data?.items?.get(0)?.volumeInfo?.imageLinks?.smallThumbnail

            imgUrl?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                imgView.load(imgUri)
            }
        }
    }
}