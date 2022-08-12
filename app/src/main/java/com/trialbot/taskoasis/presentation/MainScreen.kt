package com.trialbot.taskoasis.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.taskoasis.navigation.RootNavigator
import com.trialbot.taskoasis.navigation.MainNavGraph
import com.trialbot.taskoasis.presentation.components.AppBottomNavBar

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    startDestination: NavGraphSpec,
    navigator: RootNavigator
) {
    val navController = rememberNavController()

    Scaffold(
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
            navGraph = MainNavGraph,
            startRoute = startDestination,
            dependenciesContainerBuilder = {
                dependency(navigator)
            }
        )
    }
}