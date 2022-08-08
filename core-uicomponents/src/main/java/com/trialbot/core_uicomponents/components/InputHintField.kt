package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.inputStrokeColor

/**
 * Input field with hint when it blank.
 * Can be the password field if [KeyboardType] of [keyboardOptions] is equal to [KeyboardType.Password].
 *
 * Important thing: you need to deal with [Modifier.sizeIn] instead of plain size.
 * You need to specify height as a minHeight and let it become a bit bigger to error text can be shown.
 */
@Composable
fun InputHintField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    passwordVisibility: Boolean? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        if (isError)
            Icon(
                painter = painterResource(id = TaskOasisIcons.errorCircle),
                contentDescription = "error",
                tint = MaterialTheme.colors.error
            )
    },
) {
    OutlinedTextFieldValidation(
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
        error = error,
        isError = isError,
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisibility == null || passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon
    )
}

@Preview(showBackground = true)
@Composable
fun InputHintFieldPreview() {
    TaskOasisTheme() {
        var text by remember { mutableStateOf("") }

        InputHintField(
            modifier = Modifier
                .padding(top = 134.dp)
                .sizeIn(maxWidth = 336.dp, minHeight = 63.dp),
            value = text,
            hint = "Input text",
            onValueChanged = { text = it },
            isError = text == "error",
            error = if (text == "error") "Error text" else ""
        )
    }
}