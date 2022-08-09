package com.trialbot.taskoasis.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trialbot.feature_auth.presentation.ui.screens.AuthNavigator
import com.trialbot.feature_auth.presentation.ui.screens.destinations.LoginScreenDestination
import com.trialbot.feature_auth.presentation.ui.screens.destinations.RegisterScreenDestination

class RootNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : AuthNavigator {


    override fun navigateHome() {
        TODO("Not yet implemented")
    }

    override fun navigateNext() {
        val nextDestination = when (currentDestination) {
            LoginScreenDestination -> RegisterScreenDestination
            RegisterScreenDestination -> LoginScreenDestination
            else -> throw RuntimeException("Trying to use auth navigator from a non auth screen")
        }
        navController.navigate(nextDestination) {
            launchSingleTop = true
        }
    }
}