package com.example.counter.data.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CounterViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count : StateFlow<Int> = _count

    fun incrementCount(){
        _count.update { _count -> _count + 1 }
    }

    fun decrementCount(){
        _count.update { _count -> _count - 1 }
    }

    fun resetCount(){
        _count.update { 0 }
    }

}