package com.example.core.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.result.Result
import com.example.core.result.asResult
import com.example.core.state.AltUiState
import com.example.core.state.UiState
import com.example.data.repository.CountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CounterViewModel @Inject constructor(
    private val countRepository: CountRepository
) : ViewModel() {

    private val _events: MutableSharedFlow<com.example.core.event.CounterEvent> = MutableSharedFlow()
    private val event = _events.asSharedFlow()

    // V2 - Using Result
    private val counter: Flow<Result<Int>> = countRepository.getCount().asResult()

    private val _uiState = MutableStateFlow(AltUiState.Loading)
    val uiState: StateFlow<AltUiState> = _uiState

    // V1 - Not using Result
    private val _state = MutableStateFlow(UiState.Default)
    val state: StateFlow<UiState> = _state

    private val countFlow = countRepository.getCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        // V1 without checking result
        viewModelScope.launch {
            countFlow.collect { count ->
                Log.d("Collected", "$count")
                count?.let {
                    _state.update { uiState ->
                        uiState.copy(count = count)
                    }
                }
            }
        }
        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        } // V2 with checking result
        viewModelScope.launch {
            counter.collect { counterResult ->
                val counterUI =
                    when (counterResult) {
                        is Result.Success -> AltUiState(
                            count = counterResult.data,
                            loading = false,
                            error = ""
                        )
                        is Result.Loading -> AltUiState.Loading
                        is Result.Error -> AltUiState.Error
                    }
                //TODO Which way is better?
                _uiState.update { altUiState ->
                    altUiState.copy(
                        count = counterUI.count,
                        loading = counterUI.loading,
                        error = counterUI.error
                    )
                }
                //_uiState.value = counterUI
            }
        }
    }

    fun postEvent(event: com.example.core.event.CounterEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    // TODO Best way to update count? use event value passed??
    fun handleEvent(event: com.example.core.event.CounterEvent) {
        when (event) {
            is com.example.core.event.CounterEvent.IncrementCount -> {
                //val newCount = state.value.count + 1
                val eventCount = event.newCount + 1
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(eventCount)
                    Log.d("Counter", "Minus state=${state.value.count}")
                }
            }

            is com.example.core.event.CounterEvent.DecrementCount -> {
                val newCount = state.value.count - 1
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Minus state=${state.value.count}")
                }
            }
            is com.example.core.event.CounterEvent.ResetCount -> {
                val newCount = 0
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Reset state=${state.value.count}")
                }
            }
        }
    }

}