package com.example.counter.data.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counter.data.database.Count
import com.example.counter.data.repository.CountRepository
import com.example.counter.data.result.Result
import com.example.counter.data.result.asResult
import com.example.counter.ui.event.CounterEvent
import com.example.counter.ui.state.CounterState
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

    // Observe counter information
//    private val counter : Flow<Result<Count>> = countRepository.getCount().asResult()
//
//    val uiState: StateFlow<UiState> = combine(counter, counter) {counterOneResult, counterTwoResult ->
//        val counter: CounterState =
//            if (counterOneResult is Result.Success) {
//                val count = counterOneResult.data
//                CounterState.Success(count)
//            } else if (counterOneResult is Result.Loading){
//                CounterState.Loading
//            } else {
//                CounterState.Error
//            }
//        UiState(counter)
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState(CounterState.Success.Default))

    private val _events: MutableSharedFlow<CounterEvent> = MutableSharedFlow()
    private val event = _events.asSharedFlow()

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
        } // Separate coroutines??
        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    fun postEvent(event: CounterEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    // Any way to access the counter passed to the event? Extra code in Event classes?
    fun handleEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.IncrementCount -> {
                val newCount = state.value.counter.copy(count = state.value.counter.count + 1)
                Log.d("Counter", "New count: ${newCount.count}")
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Add state=${state.value.counter.count}")
                }
            }
            is CounterEvent.DecrementCount -> {
                val newCount = state.value.counter.copy(count = state.value.counter.count - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Minus state=${state.value.counter.count}")
                }
            }
            is CounterEvent.ResetCount -> {
                val newCount = state.value.counter.copy(count = 0)
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Reset state=${state.value.counter}")
                }
            }
        }
    }



//    fun incrementCount() {
//        val newCount = state.value.counter.copy(count = state.value.counter.count + 1)
//        Log.d("Counter", "New count: ${newCount.count}")
//        viewModelScope.launch(Dispatchers.IO) {
//            countRepository.updateCount(newCount)
//            Log.d("Counter", "Add state=${state.value.counter.count}")
//        }
//    }
//
//    fun decrementCount() {
//        val newCount = state.value.counter.copy(count = state.value.counter.count - 1)
//        viewModelScope.launch(Dispatchers.IO) {
//            countRepository.updateCount(newCount)
//            Log.d("Counter", "Minus state=${state.value.counter.count}")
//        }
//    }
//
//    fun resetCount() {
//        val newCount = state.value.counter.copy(count = 0)
//        viewModelScope.launch(Dispatchers.IO) {
//            countRepository.updateCount(newCount)
//            Log.d("Counter", "Reset state=${state.value.counter}")
//        }
//    }

}