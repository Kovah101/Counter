package com.example.counter.data.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counter.data.database.Count
import com.example.counter.data.repository.CountRepository
import com.example.counter.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CounterViewModel @Inject constructor(
    private val countRepository: CountRepository
) : ViewModel() {

    private val counter = countRepository.getCount().map { counter ->
        UiState(counter)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Default)

    private val _state = MutableStateFlow(UiState.Default)
    val state : StateFlow<UiState> = _state


    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (countRepository.tableCount() == 0) {
                Log.d("Counter", "Counter is empty, inserting fresh 0")
                countRepository.insertCount()
            } else _state.value = UiState(countRepository.getCount().first())
            Log.d("Counter", "Initial count=${state.value.counter.count}")
        }
    }

    fun incrementCount() {
        val newCount = state.value.counter
        newCount.count++
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Add state=${state.value.counter.count}")
            Log.d("Counter", "Add _state=${_state.value.counter.count}")
            Log.d("Counter", "Add counter=${counter.value.counter.count}")
            //_state.value = UiState(newCount)
        }
    }

    fun decrementCount() {
        val newCount = state.value.counter
        newCount.count--
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Minus state=${state.value.counter.count}")
            Log.d("Counter", "Minus _state=${_state.value.counter.count}")
            Log.d("Counter", "Minus counter=${counter.value.counter.count}")
           // _state.value = UiState(newCount)
        }
    }

    fun resetCount() {
        val newCount = state.value.counter
        newCount.count = 0
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Reset state=${state.value.counter.count}")
            Log.d("Counter", "Reset _state=${_state.value.counter.count}")
            Log.d("Counter", "Reset counter=${counter.value.counter.count}")
            //_state.value = UiState(newCount)
        }
    }

}