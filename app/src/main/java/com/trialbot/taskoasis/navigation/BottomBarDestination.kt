package com.trialbot.taskoasis.navigation

import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons

enum class BottomBarDestination(
    val icon: TaskOasisIcon,
    val screenName: String
) {
    Home(
        icon = TaskOasisIcon.of(TaskOasisIcons.home),
        screenName = "Home"
    ),
    Tasks(
        icon = TaskOasisIcon.of(TaskOasisIcons.bulletedList),
        screenName = "Tasks"
    ),
    Empty(
        icon = TaskOasisIcon.of(TaskOasisIcons.home),
        screenName = "Empty item"
    ),
    Habits(
        icon = TaskOasisIcon.of(TaskOasisIcons.habit),
        screenName = "Habits"
    ),
    Profile(
        icon = TaskOasisIcon.of(TaskOasisIcons.avatar),
        screenName = "Profile"
    )
}