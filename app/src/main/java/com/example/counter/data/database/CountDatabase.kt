package com.example.counter.data.database

import androidx.room.*

@Database(entities = [Count::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountDatabase : RoomDatabase() {
    abstract fun countDao(): CountDAO
}