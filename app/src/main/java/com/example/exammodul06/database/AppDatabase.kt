package com.example.exammodul06.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.exammodul06.database.converter.CardRespTypeConverter
import com.example.exammodul06.database.dao.RoomDao
import com.example.exammodul06.database.entity.CardRoom

@Database(entities = [CardRoom::class], version = 2)
abstract class AppDatabase : RoomDatabase(){

    abstract val roomDao: RoomDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // this is for to allow (get,set,delete,remove) data in Room Database, Where in Main Thread
                    .build()
            }

            return instance!!
        }
    }


}