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

    private val _state = MutableStateFlow(UiState.Default)
    val state: StateFlow<UiState> = _state

    private val countFlow = countRepository.getCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        viewModelScope.launch {
            countFlow.collect { count ->
                Log.d("Collected", "$count")
                count?.let {
                    _state.update { uiState ->
                        uiState.copy(counter = count)
                    }
                }
            }
        }
    }



    fun incrementCount() {
        val newCount = state.value.counter.copy(count = state.value.counter.count + 1)
        Log.d("Counter", "New count: ${newCount.count}")
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Add state=${state.value.counter.count}")
        }
    }

    fun decrementCount() {
        val newCount = state.value.counter.copy(count = state.value.counter.count - 1)
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Minus state=${state.value.counter.count}")
        }
    }

    fun resetCount() {
        val newCount = state.value.counter.copy(count = 0)
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter", "Reset state=${state.value.counter}")
        }
    }

}