package com.trialbot.taskoasis.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trialbot.feature_auth.presentation.screens.AuthNavigator

class RootNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : AuthNavigator {


    override fun navigateHome() {
        TODO("Not yet implemented")
    }

    override fun navigateNext() {
        TODO("Not yet implemented")
    }
}