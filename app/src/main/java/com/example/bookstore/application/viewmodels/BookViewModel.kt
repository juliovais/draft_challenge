package com.example.bookstore.application.viewmodels

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.interfaces.retrofit.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.core.room.VolumeEntity

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val appContext: Application
) : ViewModel() {

    //private val _status = MutableLiveData<MarsApiStatus>()
    //val status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData<List<CoverImage>>()
    val photos: LiveData<List<CoverImage>> = _photos

    fun test() {

        viewModelScope.launch {
            val response = repository.getVolumes("ios", 20, 0)

            val data = response.data

            data?.items?.forEach { item ->

                val volume = VolumeEntity(
                    title = item.volumeInfo.title,
                    authors = item.volumeInfo.authors.joinToString(separator = ", "),
                    description = item.volumeInfo.description
                )
                item.volumeInfo.imageLinks?.thumbnail.let {

                    volume.thumbnail = it
                }

                item.volumeInfo.saleInfo?.buyLink.let {

                    volume.buyLink = it
                }


                BookRoomDatabase.getDatabase(appContext).bookDao().insert(volume)
            }

            val volumes = BookRoomDatabase.getDatabase(appContext).bookDao().getvolumes()
            val covers = mutableListOf<CoverImage>()

            volumes.forEach { volume ->

                volume.thumbnail?.let {

                    val cover = CoverImage(volume.id.toString(), it)
                    covers.add(cover)
                }
            }

            _photos.postValue(covers)
        }
    }
}