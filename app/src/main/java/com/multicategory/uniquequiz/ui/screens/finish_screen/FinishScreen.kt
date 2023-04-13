package com.multicategory.uniquequiz.ui.screens.finish_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.multicategory.uniquequiz.R
import com.multicategory.uniquequiz.ui.navigation.Screens
import com.multicategory.uniquequiz.ui.utils.AMOUNT_QUESTIONS

@Composable
fun FinishScreen(navController: NavController, score: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
            Text(
                text = stringResource(R.string.your_score, score, AMOUNT_QUESTIONS),
                style = MaterialTheme.typography.h1
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier.padding(14.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        navController.navigate(Screens.CategoryScreen.withArgs())
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = stringResource(R.string.main_menu),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                Button(
                    modifier = Modifier.padding(14.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = stringResource(R.string.play_again),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
//}