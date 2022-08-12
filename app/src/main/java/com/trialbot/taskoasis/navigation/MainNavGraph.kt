package com.trialbot.taskoasis.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.feature_habits.presentation.screens.HabitsNavGraph
import com.trialbot.feature_home.presentation.screens.HomeNavGraph
import com.trialbot.feature_profile.presentation.screens.ProfileNavGraph
import com.trialbot.feature_tasks.presentation.screens.TasksNavGraph

object MainNavGraph : NavGraphSpec {
    override val route = "main"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = HomeNavGraph

    override val nestedNavGraphs = listOf(
        HomeNavGraph,
        TasksNavGraph,
        HabitsNavGraph,
        ProfileNavGraph
    )
}