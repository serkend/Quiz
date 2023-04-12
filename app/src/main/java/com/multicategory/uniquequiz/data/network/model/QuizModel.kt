package com.multicategory.uniquequiz.data.network.model

data class QuizModel(
    val response_code: Int,
    val results: List<Result>
)