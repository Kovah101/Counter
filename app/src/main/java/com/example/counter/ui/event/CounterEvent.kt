package com.example.counter.ui.event

import com.example.counter.data.database.Count

sealed interface CounterEvent {
    data class IncrementCount(val newCount: Int) : CounterEvent
    data class DecrementCount(val newCount: Int) : CounterEvent
    data class ResetCount(val newCount: Int) : CounterEvent
}