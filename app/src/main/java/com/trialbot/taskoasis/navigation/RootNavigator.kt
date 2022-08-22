package com.trialbot.taskoasis.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trialbot.feature_auth.AuthNavigator
import com.trialbot.feature_auth.presentation.ui.screens.destinations.AuthDirectionDestination
import com.trialbot.feature_auth.presentation.ui.screens.destinations.LoginScreenDestination
import com.trialbot.feature_auth.presentation.ui.screens.destinations.RegisterScreenDestination
import com.trialbot.feature_tasks.TasksNavigator
import com.trialbot.taskoasis.presentation.destinations.MainScreenDestination

class RootNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : AuthNavigator, TasksNavigator {


    override fun navigateHome() {
        navController.navigate(MainScreenDestination) {
            popUpTo(currentDestination) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    override fun navigateNext() {
        currentDestination as? AuthDirectionDestination
            ?: throw RuntimeException("Trying to use auth navigator from a non auth screen")

        val nextDestination = when (currentDestination) {
            LoginScreenDestination -> RegisterScreenDestination
            RegisterScreenDestination -> LoginScreenDestination
        }

        navController.navigate(nextDestination) {
            popUpTo(currentDestination) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    override fun navigateToEdit() {
        // TODO
    }

    override fun navigateUp() {
        navController.navigateUp()
    }
}