package com.multicategory.uniquequiz.ui.screens.quiz_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.multicategory.uniquequiz.R
import com.multicategory.uniquequiz.data.network.model.QuizModel
import com.multicategory.uniquequiz.ui.components.CustomSnackbar
import com.multicategory.uniquequiz.ui.screens.quiz_screen.states.QuizState
import com.multicategory.uniquequiz.ui.screens.quiz_screen.viewmodel.QuizViewModel
import com.multicategory.uniquequiz.data.network.model.Result
import com.multicategory.uniquequiz.ui.navigation.Screens
import com.multicategory.uniquequiz.ui.utils.AMOUNT_QUESTIONS

@Composable
fun QuizScreen(
    category: String,
    navController: NavController,
    category_id: String,
    difficulty: String,
    scaffoldState: ScaffoldState,
    viewModel: QuizViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        Log.e("TAG", "QuizScreen: $category_id $difficulty")
        viewModel.getQuiz(category = category_id, difficulty = difficulty)
    }

    when (val state = viewModel.quizState.collectAsState().value) {
        is QuizState.Success -> {
            QuizContent(quiz = state.data, viewModel = viewModel, navController = navController)
        }
        is QuizState.Error -> {
//            QuizContent(
//                emptyList(),
//                isLoading = true
//            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                CustomSnackbar(
                    scaffoldState = scaffoldState,
                    snackbarData = "Check internet connection"
                )
            }
        }
        QuizState.Loading -> {

        }
    }
}

@Composable
fun QuizContent(quiz: QuizModel, viewModel: QuizViewModel, navController: NavController) {
//    var currentQuestionNumber = viewModel.counter
    QuizItem(
        viewModel = viewModel,
        navController
    )
}

@Composable
fun QuizItem(
    viewModel: QuizViewModel,
    navController: NavController
) {
    val questionState = viewModel.questionState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(0.1f), contentAlignment = Alignment.Center) {
            Text(
                text = questionState.result?.category ?: "Some Category",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )
        }
        Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
            Text(
                text = questionState.result?.question ?: "Some Question",
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
        }
        Text(
            modifier = Modifier.weight(0.1f),
            text = stringResource(
                R.string.question_counter,
                questionState.counter,
                AMOUNT_QUESTIONS
            ),
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.weight(0.1f),
            text = stringResource(R.string.choose_answer),
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.weight(1.5f),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            questionState.questions.forEachIndexed { index, answer ->
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        viewModel.onClickButton(index)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        if (questionState.showResults && answer == questionState.result?.correct_answer) Color.Green
                        else if (questionState.showResults && answer != questionState.result?.correct_answer
                            && questionState.clickedIndex == index
                        ) Color.Red
                        else MaterialTheme.colors.surface
                    )
                ) {

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = answer,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }

        AnimatedVisibility(visible = questionState.showResults) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        if (questionState.finished) {
                            navController.navigate(Screens.FinishScreen.withArgs(questionState.correctAnswers.toString()))
                            viewModel.resetQuiz()
                        } else {
                            viewModel.nextQuestion()
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text =  if (questionState.finished) stringResource(R.string.finish) else stringResource(
                            R.string.next
                        ),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

