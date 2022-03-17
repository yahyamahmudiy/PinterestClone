package com.example.pinterest.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pinterest.Model.Photos

@Database(entities = [Photos::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object{
        private var instance:AppDatabase?=null

        fun getInstance(context: Context):AppDatabase{
            if (instance==null){
                instance= Room.databaseBuilder(context,AppDatabase::class.java,"photos.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

}