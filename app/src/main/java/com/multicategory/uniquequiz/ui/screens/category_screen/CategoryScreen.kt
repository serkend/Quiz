package com.multicategory.uniquequiz.ui.screens.category_screen

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.multicategory.uniquequiz.R
import com.multicategory.uniquequiz.data.network.model.Categories
import com.multicategory.uniquequiz.data.network.model.TriviaCategory
import com.multicategory.uniquequiz.ui.components.CustomSnackbar
import com.multicategory.uniquequiz.ui.navigation.Screens
import com.multicategory.uniquequiz.ui.screens.entities.Difficulty
import com.multicategory.uniquequiz.ui.screens.category_screen.states.CategoryState
import com.multicategory.uniquequiz.ui.screens.category_screen.viewmodel.CategoryViewModel
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_1
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_2
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_3
import com.multicategory.uniquequiz.ui.utils.shimmerEffect

@Composable
fun CategoryScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    when (val state = viewModel.categoriesState.collectAsState().value) {
        is CategoryState.Success -> {
            CategoriesContent(categories = state.data, navController = navController)
        }
        is CategoryState.Error -> {
            ShimmerContent()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                CustomSnackbar(
                    scaffoldState = scaffoldState,
                    snackbarData = stringResource(id = R.string.check_internet_connection)
                )
            }
        }
        CategoryState.Loading -> {
            ShimmerContent()
        }
    }
}

@Composable
fun CategoriesContent(categories: List<TriviaCategory>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category, navController)
        }
    }

}

@Composable
fun ShimmerContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(8) {
            ShimmerItem()
        }
    }
}

@Composable
fun CategoryItem(category: TriviaCategory, navController: NavController) {
    var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate(
                Screens.QuizScreen.withArgs(
                    category.name.lowercase(),
                    category.id.toString(),
                    selectedDifficulty.value
                )
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(text = category.name, style = MaterialTheme.typography.h2)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.choose_difficulty),
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
//            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DifficultyButton(
                    difficulty = Difficulty.EASY,
                    selectedDifficulty = selectedDifficulty
                ) { selectedDifficulty = it }
                DifficultyButton(
                    difficulty = Difficulty.MEDIUM,
                    selectedDifficulty = selectedDifficulty
                ) { selectedDifficulty = it }
                DifficultyButton(
                    difficulty = Difficulty.HARD,
                    selectedDifficulty = selectedDifficulty
                ) { selectedDifficulty = it }
            }
        }
    }
}

@Composable
fun ShimmerItem() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .shimmerEffect()

            )
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
fun DifficultyButton(
    modifier: Modifier = Modifier,
    selectedDifficulty: Difficulty = Difficulty.EASY,
    difficulty: Difficulty,
    onClick: (Difficulty) -> Unit
) {
    Button(
        modifier = modifier.size(32.dp),
        onClick = { onClick(difficulty) },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            if (selectedDifficulty.ordinal >= difficulty.ordinal) MaterialTheme.colors.primary
            else Color.Gray
        )
    ) {}
}