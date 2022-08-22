package com.trialbot.feature_tasks.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.trialbot.feature_tasks.TasksNavigator
import kotlinx.coroutines.launch

enum class TabItems(val title: String, val content: @Composable () -> Unit) {
    LIST(
        title = "List",
        content = { ListTabView(taskNavigator) }
    ),
    CALENDAR(
        title = "Calendar",
        content = { CalendarTabView() }
    )
}

lateinit var taskNavigator: TasksNavigator


@OptIn(ExperimentalPagerApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun TasksScreen(
    navigator: TasksNavigator
) {
    taskNavigator = navigator

    val pagerState = rememberPagerState(0)
    Surface(color = MaterialTheme.colors.background) {
        Column {
            Tabs(pagerState = pagerState)
            TabsContent(pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        TabItems.values().forEachIndexed { index, tabItem ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(
                    text = tabItem.title,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                ) }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(count = TabItems.values().size, state = pagerState) { pageIndex ->
        TabItems.values()[pageIndex].content()
    }
}