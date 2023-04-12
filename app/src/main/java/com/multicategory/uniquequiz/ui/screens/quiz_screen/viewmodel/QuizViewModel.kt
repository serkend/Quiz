package com.multicategory.uniquequiz.ui.screens.quiz_screen.viewmodel

import android.text.Html
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multicategory.uniquequiz.data.network.model.QuizModel
import com.multicategory.uniquequiz.data.network.repository.QuizNetworkRepository
import com.multicategory.uniquequiz.data.network.states.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.multicategory.uniquequiz.data.network.model.Result
import com.multicategory.uniquequiz.ui.screens.entities.Difficulty
import com.multicategory.uniquequiz.ui.screens.quiz_screen.states.QuizState
import com.multicategory.uniquequiz.ui.utils.AMOUNT_QUESTIONS
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val repository: QuizNetworkRepository) :
    ViewModel() {

    private val _quizState: MutableStateFlow<QuizState<QuizModel>> =
        MutableStateFlow(QuizState.Loading)
    val quizState = _quizState.asStateFlow()

    private val _correctAnswers = mutableStateOf(0)
    val correctAnswers: State<Int> = _correctAnswers

    var counter = mutableStateOf(0)

    private var _questions = MutableStateFlow(emptyList<List<String>>())
    var questions = _questions.asStateFlow()

//    fun getCurrentQuestion(questions: List<Result>): Result {
    // val currentQuestion = questions[counter]
    //_counter++
    // return currentQuestion
//    }

    fun nextQuestion() {
        if(counter.value < AMOUNT_QUESTIONS) {
        //    _questions.value = getShuffledList( quizState. )
            counter.value++
        }
    }

    fun getShuffledList(quizQuestion: Result): List<String> {
        val answers = quizQuestion.incorrect_answers.toMutableList()
        answers.add(quizQuestion.correct_answer)
        answers.shuffle()
        return answers
    }

    fun checkIfAnswerIsCorrect(answer: String, correctAnswer: String) {
        if (answer == correctAnswer) {
            _correctAnswers.value++
        }
    }

    fun getQuiz(difficulty: String, category: String) {
        viewModelScope.launch {
            repository.getQuiz(difficulty, category).collect { response ->
                when (response) {
                    is ResponseStatus.SuccessResponse -> {
                        _quizState.value = QuizState.Success(response.data)
                    }
                    is ResponseStatus.Loading -> {
                        _quizState.value = QuizState.Loading
                    }
                    is ResponseStatus.ErrorResponse -> {
                        _quizState.value = QuizState.Error(response.error)
                    }
                }
            }
        }
    }

    fun parseQuestion(quizQuestion: String): String =
        Html.fromHtml(quizQuestion, Html.FROM_HTML_MODE_LEGACY).toString()
}