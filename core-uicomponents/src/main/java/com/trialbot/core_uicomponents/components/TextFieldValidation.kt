package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.disabledColor

data class TextFieldSizes(
    val minHeight: Dp? = null,
    val maxHeight: Dp? = null
)

@Suppress("SENSELESS_COMPARISON")
@Composable
fun OutlinedTextFieldValidation(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldSizes: TextFieldSizes = TextFieldSizes(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (error.isNotEmpty())
            Icon(
                painter = painterResource(id = TaskOasisIcons.errorCircle),
                contentDescription = "error",
                tint = MaterialTheme.colors.error
            )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        disabledTextColor = MaterialTheme.colors.disabledColor
    )

) {

    Column (modifier = modifier) {
        OutlinedTextField(
            enabled = enabled,
            readOnly = readOnly,
            value = value,
            onValueChange = onValueChange,
            modifier = if (textFieldSizes.maxHeight != null) {
                Modifier
                    .sizeIn(maxHeight = textFieldSizes.maxHeight)
                    .fillMaxWidth()
            } else if (textFieldSizes.minHeight != null) {
                Modifier
                    .sizeIn(minHeight = textFieldSizes.minHeight)
                    .fillMaxWidth()
            } else if (textFieldSizes.maxHeight != null && textFieldSizes.minHeight != null) {
                Modifier
                    .sizeIn(
                        minHeight = textFieldSizes.minHeight,
                        maxHeight = textFieldSizes.maxHeight
                    )
                    .fillMaxWidth()
            } else {
                Modifier.fillMaxWidth()
            },
            singleLine = singleLine,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}

@Composable
fun FilledTextFieldValidation(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (error.isNotEmpty())
            Icon(
                painter = painterResource(id = TaskOasisIcons.errorCircle),
                contentDescription = "error",
                tint = MaterialTheme.colors.error
            )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors (
        disabledTextColor = MaterialTheme.colors.disabledColor
    ),
    focusRequester: FocusRequester = FocusRequester()
) {
    Column (modifier = modifier) {
        TextField(
            enabled = enabled,
            readOnly = readOnly,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            singleLine = singleLine,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OutlineTextFieldValidationPreview() {
    TaskOasisTheme() {
        var text by remember { mutableStateOf("") }

        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()) {
            OutlinedTextFieldValidation(
                value = text,
                onValueChange = { text = it },
                isError = text == "error",
                error = if (text == "error") "Error text" else "",
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            )
            Spacer(Modifier.height(20.dp))
            FilledTextFieldValidation(
                value = text,
                onValueChange = { text = it },
                isError = text == "error",
                error = if (text == "error") "Error text" else "",
                colors = TextFieldDefaults.textFieldColors (
                    backgroundColor = MaterialTheme.colors.background
                )
            )
        }
    }
}