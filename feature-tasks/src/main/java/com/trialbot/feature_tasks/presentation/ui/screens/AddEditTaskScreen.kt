package com.trialbot.feature_tasks.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.trialbot.feature_tasks.TasksNavigator
import com.trialbot.feature_tasks.presentation.viewmodels.AddEditTaskViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Destination
@Composable
fun AddEditTaskScreen(
    navigator: TasksNavigator,
    taskId: Int = -1,
    newTasDate: LocalDateTime? = null,
//    viewModel: AddEditTaskViewModel = koinViewModel()
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Add/edit task screen",
            textAlign = TextAlign.Center
        )
    }
}