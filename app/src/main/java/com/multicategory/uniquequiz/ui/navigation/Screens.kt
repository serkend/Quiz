package com.multicategory.uniquequiz.ui.navigation

sealed class Screens(val route: String) {
    object CategoryScreen : Screens(route = "CategoryScreen")
    object QuizScreen : Screens(route = "QuizScreen")
    object FinishScreen : Screens(route = "FinishScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object{
        const val SCORE = "score"
        const val CATEGORY = "category"
        const val DIFFICULTY = "difficulty"
        const val CATEGORY_ID = "category_id"
    }
}