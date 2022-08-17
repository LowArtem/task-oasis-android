package com.trialbot.taskoasis.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.taskoasis.navigation.BottomBarDestination

@Composable
fun AppTopBar(
    navController: NavController,
    onNavDrawerButtonClick: () -> Unit
) {
    val currentDestination = navController.currentDestinationAsState()
    val title = (BottomBarDestination.values()
        .find { it.direction == currentDestination.value } ?: BottomBarDestination.Home).screenName

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = {
            IconButton(
                onClick = onNavDrawerButtonClick
            ) {
                Icon(
                    painter = painterResource(id = TaskOasisIcons.burgerMenu),
                    contentDescription = "nav drawer",
                    modifier = Modifier.size(35.dp, 35.dp)
                )
            }
        },
        modifier = Modifier.padding(horizontal = 12.dp),
        elevation = 0.dp
    )
}