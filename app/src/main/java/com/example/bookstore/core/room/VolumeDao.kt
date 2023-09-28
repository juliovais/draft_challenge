package com.example.bookstore.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VolumeDao {

    @Query("SELECT * from volume where favorite = :favorite")
    suspend fun getvolumes(favorite: Boolean = false): List<VolumeEntity>

    @Query("SELECT * from volume where id = :id")
    suspend fun getvolume(id: Int): VolumeEntity

    @Insert
    suspend fun insert(volumeEntity: VolumeEntity)

    @Update
    suspend fun update(volumeEntity: VolumeEntity)
}