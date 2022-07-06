package com.example.counter.data.repository

import com.example.counter.data.database.Count
import com.example.counter.data.database.CountDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class CountRepository @Inject constructor(
    private val countDAO: CountDAO
) {

    fun getCount(): Flow<Count> = countDAO.getCount()

    suspend fun updateCount(newCount : Count) = countDAO.updateCount(newCount)

    suspend fun insertCount() = countDAO.startNewCount()

    suspend fun clearCount(count: Count) = countDAO.clearCount(count)

    suspend fun tableCount() = countDAO.tableCount()
}