package com.example.counter.ui.state

import com.example.counter.data.database.Count
import com.example.counter.data.result.Result

data class AltUiState(
    val count: Int,
    val loading: Boolean,
    val error: String,
) {
    companion object {
        val Default = AltUiState(0, false, "")
        val Loading = AltUiState(0, true, "")
        val Error = AltUiState(0,false,"ERROR")
    }
}
