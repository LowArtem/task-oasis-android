package com.trialbot.feature_tasks.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.trialbot.feature_tasks.presentation.viewmodels.ListTabViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListTabView(
    viewModel: ListTabViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "List tab",
            textAlign = TextAlign.Center
        )
    }
}