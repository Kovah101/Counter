package com.example.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.database.CountDatabase

@InstallIn(SingletonComponent::class)
@Module
class CountModule {
    @Provides
    fun provideCountDao(countDatabase: CountDatabase): CountDAO {
        return countDatabase.countDao()
    }

    @Provides
    fun provideCountDatabase(@ApplicationContext appContext: Context): CountDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            CountDatabase::class.java,
            "count_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}