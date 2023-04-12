package com.multicategory.uniquequiz.ui.screens.quiz_screen.states

data class QuestionsState(
    val showResults: Boolean,
    val clickedIndex: Int,
    val counter: Int,
    val questions:List<String>,
    val result:com.multicategory.uniquequiz.data.network.model.Result?,
    val correctAnswers:Int,
    val finished : Boolean
)