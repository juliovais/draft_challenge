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
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.core.room.VolumeEntity
import com.example.bookstore.repositories.room.RoomRepository
import com.example.bookstore.utils.ResultAPI

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repositoryAPI: BookRepository,
    private val repositoryDB: RoomRepository,
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

    private suspend fun createDatabase() {

        val response = repositoryAPI.getVolumes("ios", 20, 0)

        if (response is ResultAPI.Success) {

            response.data?.let {
                repositoryDB.populateDatabase(it)
            }
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

            _photos.value = repositoryDB.getData(_filterFavorite.value!!)
        }
    }

    fun changeFilterFavorites(isChecked: Boolean) {

        _filterFavorite.value = isChecked

        viewModelScope.launch {
            _photos.value = repositoryDB.getData(_filterFavorite.value!!)
        }
    }
}