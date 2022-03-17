package com.example.pinterest.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pinterest.Model.Photos

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePhoto(photo:Photos)

    @Query("SELECT * FROM photos")
    fun getAllPhotos():List<Photos>
}
