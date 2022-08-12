package com.trialbot.taskoasis.navigation

import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.feature_habits.presentation.screens.destinations.HabitsScreenDestination
import com.trialbot.feature_home.presentation.screens.destinations.HomeScreenDestination
import com.trialbot.feature_profile.presentation.screens.destinations.ProfileScreenDestination
import com.trialbot.feature_tasks.presentation.screens.destinations.TasksScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: TaskOasisIcon,
    val screenName: String
) {
    Home(
        direction = HomeScreenDestination,
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.home),
        screenName = "Home"
    ),
    Tasks(
        direction = TasksScreenDestination,
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.bulletedList),
        screenName = "Tasks"
    ),
    Empty(
        direction = HomeScreenDestination,
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.home),
        screenName = "Empty item"
    ),
    Habits(
        direction = HabitsScreenDestination,
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.habit),
        screenName = "Habits"
    ),
    Profile(
        direction = ProfileScreenDestination,
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.avatar),
        screenName = "Profile"
    )
}