package com.trialbot.feature_auth.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubmitButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    innerTextPadding: Dp = 80.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = innerTextPadding, vertical = 10.dp),
            color = MaterialTheme.colors.secondary
        )
    }
}