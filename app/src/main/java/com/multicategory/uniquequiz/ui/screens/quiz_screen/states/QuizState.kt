package com.multicategory.uniquequiz.ui.screens.quiz_screen.states

sealed class QuizState<out T> {
    object Loading : QuizState<Nothing>()
    data class Success<out T>(val data: T) : QuizState<T>()
    data class Error(val error: String) : QuizState<Nothing>()
}