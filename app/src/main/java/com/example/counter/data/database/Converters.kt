package com.example.counter.data.database

import androidx.room.TypeConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Converters {

    @TypeConverter
    fun fromCountToFlow(count: Count): MutableStateFlow<Count> {
        return MutableStateFlow(count)

    }

    @TypeConverter
    fun fromCountToInt(count: Count): Int {
        return count.count
    }
}