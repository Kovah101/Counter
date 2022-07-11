package com.example.counter.ui.state

import com.example.counter.data.database.Count
import com.example.counter.data.result.Result

//TODO rewrite this class to ignore CounterState and use an overall state
data class AltUiState(
    val count: Int,
    val loading: Boolean,
    val error: String,
) {
    companion object {
        val Default = AltUiState(0, false, "")
        val Loading = AltUiState(0, true, "")
        val Error = AltUiState(0,false,"ERROR")
    }
}

sealed interface CounterState {
    data class Success(val count: Count) : CounterState {
        companion object {
            val Default = Success(Count(1, 0))
        }
    }

    object Error : CounterState
    object Loading : CounterState
}