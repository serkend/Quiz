package com.multicategory.uniquequiz.ui.screens.category_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multicategory.uniquequiz.data.datastore.CategoryPreferencesRepository
import com.multicategory.uniquequiz.data.network.model.TriviaCategory
import com.multicategory.uniquequiz.data.network.repository.QuizNetworkRepository
import com.multicategory.uniquequiz.data.network.states.ResponseStatus
import com.multicategory.uniquequiz.ui.screens.category_screen.states.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: QuizNetworkRepository,
private val categoryPreferencesRepository: CategoryPreferencesRepository) :
    ViewModel() {
    private val _categoriesState: MutableStateFlow<CategoryState> =
        MutableStateFlow(CategoryState.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { response ->
                when (response) {
                    is ResponseStatus.SuccessResponse -> {
                        _categoriesState.value = CategoryState.Success(response.data.sortedBy { it.name })
                    }
                    is ResponseStatus.Loading -> {
                        _categoriesState.value = CategoryState.Loading
                    }
                    is ResponseStatus.ErrorResponse -> {
                        _categoriesState.value = CategoryState.Error(response.error)
                    }
                }
            }
        }
    }

    fun getCachedScore(category : String) = flow {
        val result = categoryPreferencesRepository.getScore(category)
        val value = result.getOrNull()
       emit(value)
    }
}