package com.trialbot.taskoasis.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.feature_auth.presentation.ui.screens.AuthNavGraph
import com.trialbot.feature_home.presentation.screens.HomeNavGraph

object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = AuthNavGraph // TODO: specify start NavGraph here

    // TODO: specify all NavGraphs here
    override val nestedNavGraphs = listOf(
        AuthNavGraph,
        HomeNavGraph
    )
}