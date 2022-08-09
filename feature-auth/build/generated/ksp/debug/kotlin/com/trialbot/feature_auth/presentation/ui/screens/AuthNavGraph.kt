package com.trialbot.feature_auth.presentation.ui.screens

import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.feature_auth.presentation.ui.screens.destinations.*

object AuthNavGraph : NavGraphSpec {
    
    override val route = "auth"
    
    override val startRoute = LoginScreenDestination
    
    override val destinationsByRoute = listOf(
		LoginScreenDestination,
		RegisterScreenDestination
    ).associateBy { it.route }

}
