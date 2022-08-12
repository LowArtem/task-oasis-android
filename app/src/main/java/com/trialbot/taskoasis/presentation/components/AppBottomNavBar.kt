package com.trialbot.taskoasis.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.taskoasis.navigation.BottomBarDestination

@Composable
fun AppBottomNavBar(
    navController: NavController
) {
    val currentDestination = navController.currentDestinationAsState()

    BottomAppBar(
        backgroundColor = MaterialTheme.colors.background
    ) {
        BottomBarDestination.values().forEachIndexed { index, destination ->
            if (index != 2) {
                BottomNavigationItem(
                    selected = currentDestination.value == destination.direction,
                    onClick = {
                        navController.navigate(destination.direction.route) {
                            popUpTo(currentDestination.value!!.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    },
                    icon = {
                        when (destination.icon) {
                            is TaskOasisIcon.DrawableResourceIcon -> {
                                Icon(
                                    painter = painterResource(id = destination.icon.id),
                                    contentDescription = "${destination.screenName} Screen",
                                    modifier = Modifier.size(35.dp, 35.dp)
                                )
                            }
                            is TaskOasisIcon.ImageVectorIcon -> {
                                Icon(
                                    imageVector = destination.icon.imageVector,
                                    contentDescription = "${destination.screenName} Screen",
                                    modifier = Modifier.size(35.dp, 35.dp)
                                )
                            }
                        }
                    },
                    unselectedContentColor = MaterialTheme.colors.secondary,
                    selectedContentColor = MaterialTheme.colors.primaryVariant
                )
            } else {
                BottomNavigationItem(
                    icon = {},
                    label = { },
                    selected = false,
                    onClick = { },
                    enabled = false
                )
            }
        }
    }
}