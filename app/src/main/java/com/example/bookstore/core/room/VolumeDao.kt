package com.example.bookstore.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VolumeDao {

    @Query("SELECT * from volume")
    suspend fun getvolumes(): List<VolumeEntity>

    @Insert
    suspend fun insert(volumeEntity: VolumeEntity)

    @Update
    suspend fun update(volumeEntity: VolumeEntity)
}