package com.trialbot.feature_auth.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Empty screen for auth screens. It contains shared elements of auth screens
 * @param name name of the screen, that will be printed on it
 * @param scaffoldState [ScaffoldState] object, that contains state of the scaffold, that is base of the screen
 * @param content remaining content of the screen
 */
@Composable
fun EmptyAuthScreen(
    name: String,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(scaffoldState = scaffoldState) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 36.dp, top = 110.dp)
                )

                content()
            }
        }
    }
}