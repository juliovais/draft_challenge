package com.example.bookstore.application.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.interfaces.retrofit.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookstore.core.SettingsDataStore
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.core.retrofit.Volume
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.core.room.VolumeEntity
import com.google.android.material.chip.Chip
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val appContext: Application
) : ViewModel() {

    private val _photos = MutableLiveData<List<CoverImage>>()
    val photos: LiveData<List<CoverImage>> = _photos

    private val _volumeDetail = MutableLiveData<VolumeEntity>()
    val volumeDetail: LiveData<VolumeEntity> = _volumeDetail

    private val _filterFavorite = MutableLiveData(false)
    val filterFavorite: LiveData<Boolean> = _filterFavorite

    fun getVolume(id: Int) {

        viewModelScope.launch {
            _volumeDetail.value = BookRoomDatabase.getDatabase(appContext).bookDao().getvolume(id)
        }
    }

    fun updateFavorite(isChecked: Boolean) {

        val volume = _volumeDetail.value
        volume?.let {

            it.favorite = isChecked

            viewModelScope.launch {
                BookRoomDatabase.getDatabase(appContext).bookDao().update(it)
            }
        }
    }

    suspend fun createDatabase() {

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
//TODO: fazer insert com List
            BookRoomDatabase.getDatabase(appContext).bookDao().insert(volume)
        }
    }

    fun loadData() {
//TODO: refatorar essa lógica usando repository
        val dataStore = SettingsDataStore()

        viewModelScope.launch {

            val databaseCreated = dataStore.readBooleanValue(appContext, "test")

            if (!databaseCreated) {

                createDatabase()

                dataStore.saveBooleanValue(appContext, "test", true)
            }

            val volumes = BookRoomDatabase.getDatabase(appContext).bookDao().getvolumes(_filterFavorite.value!!)
            val covers = mutableListOf<CoverImage>()

            volumes.forEach { volume ->

                volume.thumbnail?.let {

                    val cover = CoverImage(volume.id.toString(), it)
                    covers.add(cover)
                }
            }

            _photos.postValue(covers)
        }

//TODO: deixar tudo dentro do coroutine?


    }

    fun changeFilterFavorites(isChecked: Boolean) {

        _filterFavorite.value = isChecked

        viewModelScope.launch {
            val volumes = BookRoomDatabase.getDatabase(appContext).bookDao().getvolumes(isChecked)
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