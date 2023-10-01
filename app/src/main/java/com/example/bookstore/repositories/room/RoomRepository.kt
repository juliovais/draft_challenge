package com.example.bookstore.repositories.room

import android.app.Application
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.core.retrofit.VolumeResponse
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.core.room.VolumeEntity
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appContext: Application) {

    suspend fun populateDatabase(data: VolumeResponse) {

        val volumeList = mutableListOf<VolumeEntity>()

        data.items.forEach { item ->

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

            volumeList.add(volume)
        }
        BookRoomDatabase.getDatabase(appContext).bookDao().insert(volumeList)
    }

    suspend fun getData(filterFavorite: Boolean): List<CoverImage> {

        val volumes = BookRoomDatabase.getDatabase(appContext).bookDao()
            .getvolumes(filterFavorite)
        val covers = mutableListOf<CoverImage>()

        volumes.forEach { volume ->

            volume.thumbnail?.let {

                val cover = CoverImage(volume.id.toString(), it)
                covers.add(cover)
            }
        }

        return covers
    }

    suspend fun updateVolume(volume: VolumeEntity) {

        BookRoomDatabase.getDatabase(appContext).bookDao().update(volume)
    }

    suspend fun getVolume(id: Int): VolumeEntity {

        return BookRoomDatabase.getDatabase(appContext).bookDao().getvolume(id)
    }
}