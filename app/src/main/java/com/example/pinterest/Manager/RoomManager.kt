package com.example.pinterest.Manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pinterest.Database.PhotoDao
import com.example.pinterest.Model.Photos

@Database(entities = [Photos::class], version = 1)
abstract class RoomManager : RoomDatabase() {
    abstract fun photoDao(): PhotoDao?

    companion object {
        var INSTANCE: RoomManager? = null
        fun getDatabase(context: Context): RoomManager? {
            if (INSTANCE == null) {
                synchronized(RoomManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomManager::class.java, "app_db"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
