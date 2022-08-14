package com.trialbot.taskoasis.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.feature_auth.presentation.ui.screens.AuthNavGraph
import com.trialbot.feature_home.presentation.screens.HomeNavGraph
import com.trialbot.feature_tasks.presentation.screens.TasksNavGraph
import com.trialbot.taskoasis.navigation.RootNavigator
import com.trialbot.taskoasis.presentation.components.AppBottomNavBar
import com.trialbot.taskoasis.presentation.components.AppTopBar

@Destination(start = true)
@Composable
fun MainScreen(
    isUserLoggedIn: Boolean = false
) {
    val navController = rememberNavController()
    val startMainDestination: NavGraphSpec = HomeNavGraph // TODO: получать это из настроек (пользователь может выбрать стартовый экран)

    if (isUserLoggedIn) {
        Scaffold(
            topBar = {
                AppTopBar(
                    navController = navController,
                    onNavDrawerButtonClick = { }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = TaskOasisIcons.plus,
                        contentDescription = "Add button",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(35.dp, 35.dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                AppBottomNavBar(navController = navController)
            }
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = com.trialbot.taskoasis.navigation.RootNavGraph,
                startRoute = startMainDestination,
                dependenciesContainerBuilder = {
                    dependency(RootNavigator(destination, navController))
                }
            )
        }
    } else {
        DestinationsNavHost(
            navController = navController,
            navGraph = com.trialbot.taskoasis.navigation.RootNavGraph,
            startRoute = AuthNavGraph,
            dependenciesContainerBuilder = {
                dependency(RootNavigator(destination, navController))
            }
        )
    }
}