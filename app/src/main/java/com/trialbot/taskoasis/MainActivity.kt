package com.trialbot.taskoasis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_ui.components.InputHintField
import com.trialbot.taskoasis.ui.theme.TaskOasisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskOasisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.padding(start = 24.dp)) {
//                    Greeting("Android")
                        var text by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var passwordVisibility: Boolean by remember { mutableStateOf(false) }

                        InputHintField(
                            value = text,
                            hint = "Email",
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .size(336.dp, 63.dp),
                            onValueChanged = { text = it }
                        )

                        InputHintField(
                            value = password,
                            hint = "Password",
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .size(336.dp, 63.dp),
                            onValueChanged = { password = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            passwordVisibility = passwordVisibility,
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
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

                        Button(
                            onClick = { },
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Button",
                                Modifier.padding(horizontal = 20.dp)
                            )
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