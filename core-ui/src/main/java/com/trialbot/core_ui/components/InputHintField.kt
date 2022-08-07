package com.trialbot.core_ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trialbot.core_ui.colors.inputStrokeColor

@Composable
fun InputHintField(
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    modifier: Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = hint
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            unfocusedLabelColor = MaterialTheme.colors.inputStrokeColor,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedIndicatorColor = MaterialTheme.colors.inputStrokeColor,
            focusedIndicatorColor = MaterialTheme.colors.secondary
        ),
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
}