package com.example.counter.data.database

import androidx.room.*
import kotlinx.coroutines.flow.StateFlow


@Dao
interface CountDAO {
    //@Query("SELECT count FROM count_table WHERE countId = :countID")
    //fun getCount(countID : Int) : StateFlow<Int>

    //@Query("UPDATE count_table SET count = :newCount WHERE countId = 0 ")
    //suspend fun updateCount(newCount: Int)
    @Update
    suspend fun updateCount(count: Count)

    @Insert
    suspend fun startNewCount(count : Count)

    //@Query("DELETE FROM count_table")
    //suspend fun clear()
    @Delete
    suspend fun clearCount(count: Count)
}