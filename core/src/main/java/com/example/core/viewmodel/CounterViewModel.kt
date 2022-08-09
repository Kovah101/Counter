package com.example.core.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.result.Result
import com.example.core.result.asResult
import com.example.core.state.AltUiState
import com.example.core.event.CounterEvent
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

    private val _events: MutableSharedFlow<CounterEvent> =
        MutableSharedFlow()
    private val event = _events.asSharedFlow()

    private val counter: Flow<Result<Int>> = countRepository.getCount().asResult()

    private val _uiState = MutableStateFlow(AltUiState.Loading)
    val uiState: StateFlow<AltUiState> = _uiState

    init {
        collectEvents()
        collectCounterResult()
    }

    private fun collectEvents() {
        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    private fun collectCounterResult() {
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
                _uiState.update { altUiState ->
                    altUiState.copy(
                        count = counterUI.count,
                        loading = counterUI.loading,
                        error = counterUI.error
                    )
                }
            }
        }
    }

    fun postEvent(event: CounterEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private fun handleEvent(event: CounterEvent) {
        when (event) {

            is CounterEvent.IncrementCount -> {
                val eventCount = event.newCount + 1
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(eventCount)
                    Log.d("Counter", "Plus state=${uiState.value.count}")
                }
            }

            is CounterEvent.DecrementCount -> {
                val newCount = event.newCount - 1
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Minus state=${uiState.value.count}")
                }
            }
            is CounterEvent.ResetCount -> {
                val newCount = 0
                viewModelScope.launch(Dispatchers.IO) {
                    countRepository.updateCount(newCount)
                    Log.d("Counter", "Reset state=${uiState.value.count}")
                }
            }
        }
    }

}