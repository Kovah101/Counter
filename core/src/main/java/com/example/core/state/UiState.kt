package com.example.core.state

data class UiState(
    val count: Int

) {
    companion object{
        val Default = UiState(0)
    }

}
