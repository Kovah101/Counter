package com.example.counter.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.counter.data.repository.CountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val countRepository: CountRepository
) : ViewModel() {

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