package com.multicategory.uniquequiz.data.network.repository

import android.util.Log
import com.multicategory.uniquequiz.data.network.api.QuizApi
import com.multicategory.uniquequiz.data.network.model.QuizModel
import com.multicategory.uniquequiz.data.network.model.TriviaCategory
import com.multicategory.uniquequiz.data.network.states.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuizNetworkRepository @Inject constructor(private val apiService: QuizApi) {

    suspend fun getQuiz(difficulty: String, category: String): Flow<ResponseStatus<QuizModel>> =
        flow {
            emit(ResponseStatus.Loading)
            try {
                val data = apiService.getQuiz(difficulty, category).body()
                if (data != null) {
                    emit(ResponseStatus.SuccessResponse(data))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResponseStatus.ErrorResponse(e.message ?: "some message"))
            }
        }

    suspend fun getCategories(): Flow<ResponseStatus<List<TriviaCategory>>> = flow {
        emit(ResponseStatus.Loading)
        try {
            val categories = apiService.getCategories().body()?.trivia_categories
            if (categories != null) {
                emit(ResponseStatus.SuccessResponse(categories))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseStatus.ErrorResponse(e.message!!))
        }
    }
}