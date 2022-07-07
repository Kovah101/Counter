package com.example.counter.ui.state

import com.example.counter.data.database.Count

data class UiState(
    val counter: Count
) {
    companion object{
        val Default = UiState(Count(0,0))
    }

}