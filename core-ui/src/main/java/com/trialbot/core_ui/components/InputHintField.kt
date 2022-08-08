package com.trialbot.core_ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.trialbot.core_ui.colors.inputStrokeColor

@Composable
fun InputHintField(
    modifier: Modifier,
    value: String,
    hint: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    passwordVisibility: Boolean? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisibility == null || passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon
    )
}