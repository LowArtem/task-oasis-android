package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.inputStrokeColor

@Composable
fun InputLineField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    passwordVisibility: Boolean? = null,
    backgroundColor: Color = MaterialTheme.colors.background,
    focusRequester: FocusRequester = FocusRequester(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (isError)
            Icon(
                painter = painterResource(id = TaskOasisIcons.errorCircle),
                contentDescription = "error",
                tint = MaterialTheme.colors.error
            )
    }
) {
    FilledTextFieldValidation(
        modifier = modifier,
        value = value,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.body1
            )
        },
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            unfocusedLabelColor = MaterialTheme.colors.inputStrokeColor,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedIndicatorColor = MaterialTheme.colors.inputStrokeColor,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            backgroundColor = backgroundColor
        ),
        error = error,
        isError = isError,
        textStyle = MaterialTheme.typography.body1,
        singleLine = false,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (passwordVisibility == null || passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        focusRequester = focusRequester
    )
}

@Preview(showBackground = true)
@Composable
fun InputLineFieldPreview() {
    TaskOasisTheme() {
        var text by remember { mutableStateOf("") }

        Box(
            Modifier.fillMaxSize().background(MaterialTheme.colors.background)
        ) {
            InputLineField(
                modifier = Modifier
                    .padding(top = 134.dp)
                    .sizeIn(maxWidth = 336.dp, minHeight = 63.dp),
                value = text,
                placeholder = "Input text",
                onValueChanged = { text = it },
                isError = text == "error",
                error = if (text == "error") "Error text" else ""
            )
        }
    }
}