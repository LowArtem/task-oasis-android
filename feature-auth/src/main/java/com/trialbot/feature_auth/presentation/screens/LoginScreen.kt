package com.trialbot.feature_auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_uicomponents.components.InputHintField
import com.trialbot.feature_auth.presentation.ui.components.SubmitButton
import com.trialbot.feature_auth.presentation.ui.components.TextWithLink
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colors.background
        ) {
            Column {
                var text by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisibility: Boolean by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()

                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 36.dp, top = 121.dp)
                )

                InputHintField(
                    value = text,
                    hint = "Email",
                    modifier = Modifier
                        .padding(top = 134.dp)
                        .sizeIn(maxWidth = 336.dp, minHeight = 63.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChanged = { text = it }
                )

                InputHintField(
                    value = password,
                    hint = "Password",
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .sizeIn(maxWidth = 336.dp, minHeight = 63.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChanged = { password = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    passwordVisibility = passwordVisibility,
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            if (passwordVisibility)
                                Icon(
                                    painterResource(id = TaskOasisIcons.visibilityOn),
                                    contentDescription = "Password visible"
                                )
                            else
                                Icon(
                                    painterResource(id = TaskOasisIcons.visibilityOff),
                                    contentDescription = "Password invisible"
                                )
                        }
                    }
                )

                SubmitButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(top = 78.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Log In",
                    innerTextPadding = 90.dp
                )

                TextWithLink(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally),
                    mainText = "Don’t have an account? ",
                    linkText = "Sign Up"
                ) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Link was clicked")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TaskOasisTheme {
        LoginScreen()
    }
}
