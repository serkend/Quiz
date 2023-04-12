package com.multicategory.uniquequiz.ui.screens.quiz_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import com.multicategory.uniquequiz.R
import com.multicategory.uniquequiz.data.network.model.QuizModel
import com.multicategory.uniquequiz.ui.components.CustomSnackbar
import com.multicategory.uniquequiz.ui.screens.category_screen.CategoriesContent
import com.multicategory.uniquequiz.ui.screens.category_screen.ShimmerItem
import com.multicategory.uniquequiz.ui.screens.quiz_screen.states.QuizState
import com.multicategory.uniquequiz.ui.screens.quiz_screen.viewmodel.QuizViewModel
import com.multicategory.uniquequiz.data.network.model.Result
import com.multicategory.uniquequiz.ui.screens.entities.Difficulty

@Composable
fun QuizScreen(
    category: String,
    category_id: String,
    difficulty: String,
    scaffoldState: ScaffoldState,
    viewModel: QuizViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        Log.e("TAG", "QuizScreen: $category_id $difficulty")
        viewModel.getQuiz(category = category_id, difficulty = difficulty)
    }
    val state = viewModel.quizState.collectAsState().value

    when (state) {
        is QuizState.Success -> {
            QuizContent(quiz = state.data, viewModel = viewModel)
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
fun QuizContent(quiz: QuizModel, viewModel: QuizViewModel) {
    val counter by viewModel.counter
    var questions = viewModel.getShuffledList(quiz.results[counter])
    QuizItem(
        result = quiz.results[counter],
        questions = questions,
        category = quiz.results[counter].category
    ) {
        viewModel.nextQuestion()
    }
}

@Composable
fun QuizItem(
    result: Result,
    questions: List<String>,
    category: String,
    onNextQuestion: () -> Unit
) {
    var btnVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(0.1f), contentAlignment = Alignment.Center) {
            Text(
                text = category,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
        }
        Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
            Text(
                text = result.question,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.weight(0.1f),
            text = stringResource(R.string.choose_answer),
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier.weight(1.5f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            questions.forEach { answer ->
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = { btnVisibility = true }
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
        AnimatedVisibility(visible = btnVisibility) {
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
                        btnVisibility = false
                        onNextQuestion()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Next",
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

//@Composable
//fun QuizShimmer() {
//    // var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }
//    Card(modifier = Modifier.fillMaxWidth()) {
//        Column(
//            modifier = Modifier
//                .padding(12.dp)
//        ) {
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth(0.4f)
//                    .height(16.dp)
//                    .shimmerEffect()
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth(0.5f)
//                    .height(16.dp)
//                    .shimmerEffect()
//
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth(0.6f)
//                    .height(16.dp)
//                    .shimmerEffect()
//            )
//        }
//    }
//}

