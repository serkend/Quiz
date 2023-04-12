package com.multicategory.uniquequiz.data.network.states

sealed class ResponseStatus<out T> {
    object Loading : ResponseStatus<Nothing>()
    data class SuccessResponse<out T>(val data: T) : ResponseStatus<T>()
    data class ErrorResponse(val error: String) : ResponseStatus<Nothing>()
}