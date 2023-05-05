package com.multicategory.uniquequiz.ui.screens.finish_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multicategory.uniquequiz.data.datastore.CategoryPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishViewModel @Inject constructor(private val categoryPreferencesRepository: CategoryPreferencesRepository) :
    ViewModel() {

    fun saveCachedScore(score: String, key: String) {
        viewModelScope.launch {
            categoryPreferencesRepository.saveScore(score, key)
        }
    }

}