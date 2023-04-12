package com.multicategory.uniquequiz.data.network.api

import com.multicategory.uniquequiz.data.network.model.Categories
import com.multicategory.uniquequiz.data.network.model.QuizModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {
    @GET("/api.php/")
    suspend fun getQuiz(
        @Query("difficulty") difficulty: String,
        @Query("category") category: String,
        @Query("amount") amount: Int = QUESTIONS_AMOUNT,
        @Query("type") type: String = MULTIPLE
    ): Response<QuizModel>

    @GET("/api_category.php")
    suspend fun getCategories(): Response<Categories>

    companion object {
        const val MULTIPLE = "multiple"
        const val QUESTIONS_AMOUNT = 5
    }
}