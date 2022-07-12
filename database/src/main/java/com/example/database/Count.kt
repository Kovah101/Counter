package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "count_table")
data class Count(
    @PrimaryKey(autoGenerate = true)
    val countId: Int = 0,

    @ColumnInfo(name = "count", defaultValue = "0")
    val count: Int = 0
)