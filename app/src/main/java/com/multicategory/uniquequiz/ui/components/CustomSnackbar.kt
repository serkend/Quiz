package com.multicategory.uniquequiz.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CustomSnackbar(scaffoldState: ScaffoldState, snackbarData: String) {
    val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("")
            }
    }
    SnackbarHost(hostState = scaffoldState.snackbarHostState) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(),
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = snackbarData)
                Text(text = snackbarData)
            }
        }
    }
}