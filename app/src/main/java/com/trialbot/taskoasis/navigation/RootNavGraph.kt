package com.trialbot.taskoasis.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.feature_auth.presentation.ui.screens.AuthNavGraph
import com.trialbot.feature_habits.presentation.screens.HabitsNavGraph
import com.trialbot.feature_home.presentation.screens.HomeNavGraph
import com.trialbot.feature_profile.presentation.screens.ProfileNavGraph
import com.trialbot.feature_tasks.presentation.ui.screens.TasksNavGraph
import com.trialbot.taskoasis.presentation.MainNavGraph

object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = AuthNavGraph

    override val nestedNavGraphs = listOf(
        AuthNavGraph,
        MainNavGraph,
        HomeNavGraph,
        TasksNavGraph,
        HabitsNavGraph,
        ProfileNavGraph
    )
}