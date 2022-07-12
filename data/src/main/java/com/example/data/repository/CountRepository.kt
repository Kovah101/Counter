package com.example.data.repository

import com.example.database.CountDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CountRepository @Inject constructor(
    private val countDAO: CountDAO
) {

    fun getCount(): Flow<Int> = countDAO.getCount()

    suspend fun updateCount(newCount: Int) = countDAO.updateCount(
        com.example.database.Count(
            1,
            newCount
        )
    )

    suspend fun insertCount() = countDAO.startNewCount()

    suspend fun clearCount(count: com.example.database.Count) = countDAO.clearCount(count)

    suspend fun tableCount() = countDAO.tableCount()
}