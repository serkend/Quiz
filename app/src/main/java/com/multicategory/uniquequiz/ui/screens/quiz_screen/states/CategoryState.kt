package com.multicategory.uniquequiz.ui.screens.quiz_screen.states

sealed class CategoryState<out T> {
    object Loading : CategoryState<Nothing>()
    data class Success<out T>(val data: T) : CategoryState<T>()
    data class Error(val error: String) : CategoryState<Nothing>()
}