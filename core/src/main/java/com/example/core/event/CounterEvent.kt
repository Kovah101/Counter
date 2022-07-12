package com.example.core.event

sealed interface CounterEvent {
    data class IncrementCount(val newCount: Int) : CounterEvent
    data class DecrementCount(val newCount: Int) : CounterEvent
    data class ResetCount(val newCount: Int) : CounterEvent
}