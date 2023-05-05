package com.multicategory.uniquequiz.ui.navigation

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.multicategory.uniquequiz.ui.navigation.Screens.Companion.CATEGORY
import com.multicategory.uniquequiz.ui.navigation.Screens.Companion.CATEGORY_ID
import com.multicategory.uniquequiz.ui.navigation.Screens.Companion.DIFFICULTY
import com.multicategory.uniquequiz.ui.navigation.Screens.Companion.SCORE
import com.multicategory.uniquequiz.ui.screens.category_screen.CategoryScreen
import com.multicategory.uniquequiz.ui.screens.entities.Difficulty
import com.multicategory.uniquequiz.ui.screens.finish_screen.FinishScreen
import com.multicategory.uniquequiz.ui.screens.quiz_screen.QuizScreen

@Composable
fun Navigation() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.CategoryScreen.route
    ) {
        composable(Screens.CategoryScreen.route) {
            CategoryScreen(scaffoldState = scaffoldState, navController = navController)
        }
        composable(Screens.QuizScreen.withArgs("{$CATEGORY}", "{$CATEGORY_ID}", "{$DIFFICULTY}"),
            arguments = listOf(
                navArgument(CATEGORY) {
                    type = NavType.StringType
                },
                navArgument(CATEGORY_ID) {
                    type = NavType.StringType
                },
                navArgument(DIFFICULTY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            QuizScreen(
                category = entry.arguments?.getString(CATEGORY) ?: "",
                navController = navController,
                category_id = entry.arguments?.getString(CATEGORY_ID) ?: "",
                scaffoldState = scaffoldState,
                difficulty = entry.arguments?.getString(DIFFICULTY) ?: Difficulty.EASY.value
            )
        }
        composable(Screens.FinishScreen.withArgs("{$SCORE}", "{$CATEGORY}"),
            arguments = listOf(
                navArgument(SCORE) {
                    type = NavType.StringType
                },
                navArgument(CATEGORY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            FinishScreen(
                navController = navController,
                score = entry.arguments?.getString(SCORE),
                category = entry.arguments?.getString(CATEGORY)
            )
        }
    }
}