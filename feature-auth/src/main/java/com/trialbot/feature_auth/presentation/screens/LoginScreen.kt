package com.trialbot.feature_auth.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_uicomponents.components.InputHintField
import com.trialbot.feature_auth.R
import com.trialbot.feature_auth.presentation.events.LoginEvent
import com.trialbot.feature_auth.presentation.events.UiEvent
import com.trialbot.feature_auth.presentation.ui.components.EmptyAuthScreen
import com.trialbot.feature_auth.presentation.ui.components.SubmitButton
import com.trialbot.feature_auth.presentation.ui.components.TextWithLink
import com.trialbot.feature_auth.presentation.viewmodels.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

// TODO: переместить start=true на splashScreen
@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = koinViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val emailState = viewModel.email.value
    val passwordState = viewModel.password.value

    LaunchedEffect(key1 = true) {
        viewModel.uiEventsFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateToHome -> {
                    navigator.navigateHome()
                }
                is UiEvent.NavigateToSignUp -> {
                    navigator.navigateNext()
                }
                is UiEvent.ShowShackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    EmptyAuthScreen(
        name = "Log In",
        scaffoldState = scaffoldState
    ) {
        InputHintField(
            value = emailState.text,
            hint = stringResource(id = R.string.email_hint),
            modifier = Modifier
                .padding(top = 134.dp)
                .sizeIn(maxWidth = 336.dp, minHeight = 63.dp)
                .align(Alignment.CenterHorizontally),
            onValueChanged = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
            isError = emailState.validatingError.isErrorOccurred,
            error = emailState.validatingError.message
        )

        InputHintField(
            value = passwordState.text,
            hint = stringResource(R.string.password_hint),
            modifier = Modifier
                .padding(top = 40.dp)
                .sizeIn(maxWidth = 336.dp, minHeight = 63.dp)
                .align(Alignment.CenterHorizontally),
            onValueChanged = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
            isError = passwordState.validatingError.isErrorOccurred,
            error = passwordState.validatingError.message,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            passwordVisibility = passwordState.isPasswordVisible,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.onEvent(LoginEvent.PasswordVisibilityChanged)
                }) {
                    if (passwordState.isPasswordVisible)
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
            modifier = Modifier
                .padding(top = 78.dp)
                .align(Alignment.CenterHorizontally),
            text = "Log In",
            innerTextPadding = 90.dp
        ) {
            viewModel.onEvent(LoginEvent.Login)
        }

        TextWithLink(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally),
            mainText = stringResource(R.string.navigate_to_signup_helper),
            linkText = stringResource(R.string.navigate_to_signup_link)
        ) {
            viewModel.onEvent(LoginEvent.NavigateToSignUp)
        }
    }
}
