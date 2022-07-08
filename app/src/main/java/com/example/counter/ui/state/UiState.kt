package com.example.counter.ui.state

import android.support.v4.os.IResultReceiver
import com.example.counter.data.database.Count

data class UiState(
    val counter: Count
    //val counterState : CounterState
) {
    companion object{
        val Default = UiState(Count(1,0))
    }

}

sealed interface CounterState{
    data class Success(val count: Count) : CounterState {
        companion object{
            val Default = Success(Count(1,0))
        }
    }
    object Error : CounterState
    object Loading : CounterState
}