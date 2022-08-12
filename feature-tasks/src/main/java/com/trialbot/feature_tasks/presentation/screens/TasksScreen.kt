package com.trialbot.feature_tasks.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun TasksScreen(

) {
    Text(
        text = "Tasks Screen",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}