package com.example.counter.data.database

import androidx.room.TypeConverter
import kotlinx.coroutines.flow.StateFlow

class Converters {
    @TypeConverter
    fun fromIntToCount(count : Int): StateFlow<Count>? {
        //TODO THIS!!!
        val counter : Count = Count(0,count)
        val counterFlow : StateFlow<Count>
        counterFlow.value = counter
        return counterFlow?.value = counter
    }

    @TypeConverter
    fun fromCountToInt(count: Count?): Int? {
        return count?.count
    }
}