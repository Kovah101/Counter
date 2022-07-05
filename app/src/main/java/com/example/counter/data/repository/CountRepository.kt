package com.example.counter.data.repository

import com.example.counter.data.database.Count
import com.example.counter.data.database.CountDAO
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class CountRepository @Inject constructor(
    private val countDAO: CountDAO
) {

    //fun getCount(countID : Int): StateFlow<Int> = countDAO.getCount(countID)

    suspend fun updateCount(newCount : Count) = countDAO.updateCount(newCount)

    suspend fun insertCount(zeroCount : Count) = countDAO.startNewCount(zeroCount)

    suspend fun clearCount(count: Count) = countDAO.clearCount(count)
}