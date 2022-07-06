package com.example.counter.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counter.data.database.Count
import com.example.counter.data.repository.CountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class UiState(val countStateFlow: StateFlow<Count>) : MutableStateFlow<Count>

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val countRepository: CountRepository
) : ViewModel() {

    private val initialState by lazy {
        Count()
    }

    private val _state: StateFlow<Count> = countRepository.getCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialState)

    //private val _state = MutableStateFlow(counterState.value)

    val state: StateFlow<Count> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (countRepository.tableCount() == 0) {
                countRepository.insertCount()
            }
        }
    }

    fun incrementCount() {
        val newCount = state.value
        newCount.count++
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter","Add state=${state.value.count}")
            Log.d("Counter","Add _state=${_state.value.count}")
        }
    }

    fun decrementCount() {
        val newCount = state.value
        newCount.count--
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter","Minus state=${state.value.count}")
            Log.d("Counter","Minus _state=${_state.value.count}")
        }
    }

    fun resetCount() {
        val newCount = state.value
        newCount.count = 0
        viewModelScope.launch(Dispatchers.IO) {
            countRepository.updateCount(newCount)
            Log.d("Counter","Reset state=${state.value.count}")
            Log.d("Counter","Reset _state=${_state.value.count}")
        }
    }

}