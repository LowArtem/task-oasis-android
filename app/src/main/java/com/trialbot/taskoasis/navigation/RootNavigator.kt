package com.trialbot.taskoasis.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trialbot.feature_auth.presentation.screens.LoginScreenNavigator

class RootNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : LoginScreenNavigator {


    override fun navigateHome() {
        TODO("Not yet implemented")
    }

    override fun navigateToSignUp() {
        TODO("Not yet implemented")
    }
}