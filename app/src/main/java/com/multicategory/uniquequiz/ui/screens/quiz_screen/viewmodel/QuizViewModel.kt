package com.multicategory.uniquequiz.ui.screens.quiz_screen.viewmodel

import android.text.Html
import android.util.Log
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
import com.multicategory.uniquequiz.ui.screens.quiz_screen.states.QuestionsState
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

    private var results: List<Result> = emptyList()

    private val _questionState: MutableStateFlow<QuestionsState> =
        MutableStateFlow(
            QuestionsState(
                showResults = false,
                clickedIndex = 0,
                counter = 0,
                questions = emptyList(),
                result = null,
                finished = false,
                correctAnswers = 0
            )
        )
    val questionState = _questionState.asStateFlow()

    fun nextQuestion() {
        if (!checkIfLastQuestion()) {
            Log.e("TAG", "nextQuestion: ${questionState.value.counter}")
            getShuffledList(results[_questionState.value.counter])
            _questionState.value =
                _questionState.value.copy(
                    counter = _questionState.value.counter + 1,
                    showResults = false
                )
            if (checkIfLastQuestion()) {
                _questionState.value =
                    _questionState.value.copy(
                        finished = true
                    )
            }
        }
    }

    private fun checkIfLastQuestion(): Boolean {
        if (_questionState.value.counter == AMOUNT_QUESTIONS) {
            return true
        }
        return false
    }

    private fun getShuffledList(quizQuestion: Result) {
        val answers = quizQuestion.incorrect_answers.toMutableList()
        answers.add(quizQuestion.correct_answer)
        answers.shuffle()
        val parsedQuestions = answers.map { parseQuestion(it) }
        val parsedTitleQuestion = quizQuestion.copy(question = parseQuestion(quizQuestion.question))
        _questionState.value =
            _questionState.value.copy(questions = parsedQuestions, result = parsedTitleQuestion)
    }

    fun onClickButton(index: Int) {
        if (!questionState.value.showResults) {
            _questionState.value =
                _questionState.value.copy(showResults = true, clickedIndex = index)
            checkIfAnswerIsCorrect(index)
        }
    }

    private fun checkIfAnswerIsCorrect(index: Int): Boolean {
        if (questionState.value.questions[index] == questionState.value.result?.correct_answer) {
            _questionState.value =
                _questionState.value.copy(correctAnswers = _questionState.value.correctAnswers + 1)
            return true
        }
        return false
    }

    fun getQuiz(difficulty: String, category: String) {
        viewModelScope.launch {
            repository.getQuiz(difficulty, category).collect { response ->
                when (response) {
                    is ResponseStatus.SuccessResponse -> {
                        _quizState.value = QuizState.Success(response.data)
                        results = response.data.results
                        nextQuestion()
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

    fun resetQuiz() {
        _questionState.value = QuestionsState(
            showResults = false,
            clickedIndex = 0,
            counter = 0,
            questions = emptyList(),
            result = null,
            finished = false,
            correctAnswers = 0
        )
    }

    fun parseQuestion(quizQuestion: String): String =
        Html.fromHtml(quizQuestion, Html.FROM_HTML_MODE_LEGACY).toString()
}