package com.example.counter.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface CountDAO {
    @Query("SELECT * FROM count_table")
    fun getCount() : Flow<Count>

    @Insert(onConflict = REPLACE)
    suspend fun updateCount(count: Count)

//    @Insert
//    suspend fun startNewCount()
    @Query("INSERT INTO count_table DEFAULT VALUES")
    suspend fun startNewCount()

    @Delete
    suspend fun clearCount(count: Count)

    @Query("SELECT COUNT(*) FROM count_table")
    suspend fun tableCount(): Int
}