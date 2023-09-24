package com.example.bookstore.application.viewmodels

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.interfaces.retrofit.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import coil.load
import com.example.bookstore.core.retrofit.Volume
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.core.room.VolumeDao
import com.example.bookstore.core.room.VolumeEntity

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val appContext: Application
) : ViewModel() {

    fun test(imgView: ImageView) {

        viewModelScope.launch {
            val response = repository.getVolumes("ios", 20, 0)

            val data = response.data
            val imgUrl = data?.items?.get(0)?.volumeInfo?.imageLinks?.thumbnail

            imgUrl?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                imgView.load(imgUri)
            }

            data?.items?.forEach { item ->

                val volume = VolumeEntity(
                    title = item.volumeInfo.title,
                    authors = item.volumeInfo.authors.joinToString(separator = ", "),
                    description = item.volumeInfo.description
                )

                BookRoomDatabase.getDatabase(appContext).bookDao().insert(volume)
            }

            val volumes = BookRoomDatabase.getDatabase(appContext).bookDao().getvolumes()

            volumes.forEach { volume ->

                println(volume.title)
            }
        }
    }
}