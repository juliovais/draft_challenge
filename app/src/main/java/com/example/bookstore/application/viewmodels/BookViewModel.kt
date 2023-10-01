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
import com.example.bookstore.core.room.VolumeEntity
import com.example.bookstore.interfaces.room.VolumeDao
import com.example.bookstore.repositories.room.RoomRepository
import com.example.bookstore.utils.ResultAPI

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repositoryAPI: BookRepository,
    private val repositoryDB: RoomRepository,
    private val appContext: Application
) : ViewModel() {

    private val _coverList = MutableLiveData<List<CoverImage>>()
    val coverList: LiveData<List<CoverImage>> = _coverList

    private val _volumeDetail = MutableLiveData<VolumeEntity>()
    val volumeDetail: LiveData<VolumeEntity> = _volumeDetail

    private val _filterFavorite = MutableLiveData(false)
    val filterFavorite: LiveData<Boolean> = _filterFavorite

    fun getVolume(id: Int) {

        viewModelScope.launch {
            _volumeDetail.value = repositoryDB.getVolume(id)
        }
    }

    fun updateFavorite(isChecked: Boolean) {

        val volume = _volumeDetail.value
        volume?.let {

            it.favorite = isChecked

            viewModelScope.launch {
                repositoryDB.updateVolume(it)
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
        val dataStore = SettingsDataStore()

        viewModelScope.launch {

            val databaseCreated = dataStore.getBoolean(appContext, VolumeDao.FIRST_LOAD_DONE)

            if (!databaseCreated) {

                createDatabase()

                dataStore.setBoolean(appContext, VolumeDao.FIRST_LOAD_DONE, true)
            }

            _coverList.value = repositoryDB.getData(_filterFavorite.value!!)
        }
    }

    fun changeFilterFavorites(isChecked: Boolean) {

        _filterFavorite.value = isChecked

        viewModelScope.launch {
            _coverList.value = repositoryDB.getData(_filterFavorite.value!!)
        }
    }
}