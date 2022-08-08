package com.trialbot.taskoasis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskOasisTheme {
                Greeting(name = "android")
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