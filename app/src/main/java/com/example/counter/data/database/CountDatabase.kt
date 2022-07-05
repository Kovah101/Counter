package com.example.counter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Count::class], version = 1, exportSchema = false)
abstract class CountDatabase : RoomDatabase() {
    abstract fun countDao(): CountDAO

//    companion object {
//        @Volatile
//        private var INSTANCE: CountDatabase? = null
//
//        fun getInstance(context: Context): CountDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        CountDatabase::class.java,
//                        "count_database"
//                    ).fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}