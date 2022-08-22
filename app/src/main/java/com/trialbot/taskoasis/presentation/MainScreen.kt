package com.trialbot.taskoasis.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.feature_habits.presentation.screens.HabitsScreen
import com.trialbot.feature_home.presentation.screens.HomeScreen
import com.trialbot.feature_profile.presentation.screens.ProfileScreen
import com.trialbot.feature_tasks.presentation.ui.screens.TasksScreen
import com.trialbot.taskoasis.navigation.RootNavigator
import com.trialbot.taskoasis.presentation.components.AppBottomNavBar
import com.trialbot.taskoasis.presentation.components.AppTopBar

enum class BottomBarScreens(
    val title: String,
    val screen: @Composable () -> Unit,
    val onAddButtonClick: @Composable () -> Unit
) {
    HOME(
        title = "Home",
        screen = { HomeScreen() },
        onAddButtonClick = { }
    ),
    TASKS(
        title = "Tasks",
        screen = { TasksScreen(rootNavigator) },
        onAddButtonClick = { }
    ),
    EMPTY(
        title = "",
        screen = { },
        onAddButtonClick = { }
    ),
    HABITS(
        title = "Habits",
        screen = { HabitsScreen() },
        onAddButtonClick = { }
    ),
    PROFILE(
        title = "Profile",
        screen = { ProfileScreen() },
        onAddButtonClick = { }
    )
}

private lateinit var rootNavigator: RootNavigator

@OptIn(ExperimentalPagerApi::class)
@Destination(start = true)
@Composable
fun MainScreen(
    navigator: RootNavigator
) {
    rootNavigator = navigator

    // TODO: получать это из настроек (пользователь может выбрать стартовый экран)
    val pagerState = rememberPagerState(BottomBarScreens.TASKS.ordinal)

    Scaffold(
        topBar = {
            AppTopBar(
                title = BottomBarScreens.values()[pagerState.currentPage].title
            ) { }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { BottomBarScreens.values()[pagerState.currentPage].onAddButtonClick },
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
            AppBottomNavBar(pagerState = pagerState)
        }
    ) {
        HorizontalPager(count = BottomBarScreens.values().size, state = pagerState) { index ->
            BottomBarScreens.values()[index].screen()
        }
    }
}