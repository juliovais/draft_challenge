package com.example.bookstore.interfaces.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bookstore.core.room.VolumeEntity

@Dao
interface VolumeDao {

    @Query("SELECT * from volume where favorite = :favorite")
    suspend fun getVolumes(favorite: Boolean = false): List<VolumeEntity>

    @Query("SELECT * from volume where id = :id")
    suspend fun getVolume(id: Int): VolumeEntity

    @Insert
    suspend fun insert(volumeEntity: List<VolumeEntity>)

    @Update
    suspend fun update(volumeEntity: VolumeEntity)
}