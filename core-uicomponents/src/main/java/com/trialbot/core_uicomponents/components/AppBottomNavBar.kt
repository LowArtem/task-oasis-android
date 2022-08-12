package com.trialbot.core_uicomponents.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
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

@Composable
fun AppBottomNavBar(
    navController: NavController,
) {
    val currentDestination = navController.currentDestinationAsState()

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination.value == destination.direction,
                onClick = {
                    navController.navigate(destination.direction.route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    when (destination.icon) {
                        is TaskOasisIcon.DrawableResourceIcon -> {
                            Icon(
                                painter = painterResource(id = destination.icon.id),
                                contentDescription = "${destination.screenName} Screen"
                            )
                        }
                        is TaskOasisIcon.ImageVectorIcon -> {
                            Icon(
                                imageVector = destination.icon.imageVector,
                                contentDescription = "${destination.screenName} Screen"
                            )
                        }
                    }
                },
            unselectedContentColor = MaterialTheme.colors.secondary,
            selectedContentColor = MaterialTheme.colors.primaryVariant)
        }
    }
}