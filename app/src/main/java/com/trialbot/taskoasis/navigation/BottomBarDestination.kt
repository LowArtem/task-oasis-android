package com.trialbot.taskoasis.navigation

import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons

enum class BottomBarDestination(
    val icon: TaskOasisIcon,
    val screenName: String
) {
    Home(
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.home),
        screenName = "Home"
    ),
    Tasks(
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.bulletedList),
        screenName = "Tasks"
    ),
    Empty(
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.home),
        screenName = "Empty item"
    ),
    Habits(
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.habit),
        screenName = "Habits"
    ),
    Profile(
        icon = TaskOasisIcon.ImageVectorIcon(TaskOasisIcons.avatar),
        screenName = "Profile"
    )
}