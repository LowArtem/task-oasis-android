package com.trialbot.taskoasis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trialbot.core_uicomponents.components.InputHintField
import com.trialbot.feature_auth.presentation.ui.components.SubmitButton
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.poppins
import kotlinx.coroutines.launch
import com.trialbot.core_designsystem.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskOasisTheme {
                // A surface container using the 'background' color from the theme
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
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
                                    .size(336.dp, 63.dp)
                                    .align(Alignment.CenterHorizontally),
                                onValueChanged = { text = it }
                            )

                            InputHintField(
                                value = password,
                                hint = "Password",
                                modifier = Modifier
                                    .padding(top = 40.dp)
                                    .size(336.dp, 63.dp)
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
                                                painterResource(id = R.drawable.ic_visible),
                                                contentDescription = "Password visible"
                                            )
                                        else
                                            Icon(
                                                painterResource(id = R.drawable.ic_invisible),
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

                            Row(
                                modifier = Modifier
                                    .padding(top = 30.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "Don’t have an account? ",
                                    style = MaterialTheme.typography.subtitle2,
                                    color = MaterialTheme.colors.onPrimary
                                )

                                Text(
                                    modifier = Modifier.clickable {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState
                                                .showSnackbar("Sign Up button was pressed")
                                        }
                                    },
                                    text = AnnotatedString(text = "Sign Up"),
                                    style = TextStyle(
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colors.primary,
                                        textDecoration = TextDecoration.Underline
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskOasisTheme {
        Greeting("Android")
    }
}