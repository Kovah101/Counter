package com.example.counter.ui.state

import com.example.counter.data.database.Count

//TODO rewrite this class to ignore CounterState and use an overall state
data class AltUiState (
    val count : Int,
    val loading : Boolean,
    val error : String
)

sealed interface CounterState{
    data class Success(val count: Count) : CounterState {
        companion object{
            val Default = Success(Count(1,0))
        }
    }
    object Error : CounterState
    object Loading : CounterState
}