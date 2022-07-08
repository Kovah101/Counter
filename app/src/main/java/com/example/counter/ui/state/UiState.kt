package com.example.counter.ui.state

import android.support.v4.os.IResultReceiver
import com.example.counter.data.database.Count

data class UiState(
    val counter: Count

) {
    companion object{
        val Default = UiState(Count(1,0))
    }

}
