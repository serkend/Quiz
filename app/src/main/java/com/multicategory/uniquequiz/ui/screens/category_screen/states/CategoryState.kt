package com.multicategory.uniquequiz.ui.screens.category_screen.states

import com.multicategory.uniquequiz.data.network.model.TriviaCategory

sealed class CategoryState{
    object Loading : CategoryState()
    data class Success(val data: List<TriviaCategory>) : CategoryState()
    data class Error(val error: String) : CategoryState()
}