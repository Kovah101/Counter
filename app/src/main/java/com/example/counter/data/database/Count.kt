package com.example.counter.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "count_table")
data class Count(
    @PrimaryKey(autoGenerate = true)
    var countId: Int = 0,

    @ColumnInfo(name = "count", defaultValue = "0")
    var count: Int = 0
)