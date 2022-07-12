package com.example.database

import androidx.room.*

@Database(entities = [Count::class], version = 3, exportSchema = false)
abstract class CountDatabase : RoomDatabase() {
    abstract fun countDao(): CountDAO
}