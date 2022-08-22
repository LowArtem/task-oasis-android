package com.trialbot.feature_tasks.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_uicomponents.components.task.TaskGroupData
import com.trialbot.core_uicomponents.components.task.TaskGroups
import com.trialbot.feature_tasks.TasksNavigator
import com.trialbot.feature_tasks.presentation.events.TaskEvent
import com.trialbot.feature_tasks.presentation.events.UiEvent
import com.trialbot.feature_tasks.presentation.state.ListTabUiState
import com.trialbot.feature_tasks.presentation.viewmodels.ListTabViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListTabView(
    navigator: TasksNavigator,
    viewModel: ListTabViewModel = koinViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val uiState: ListTabUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventsFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowShackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.NavigateToTaskEdit -> {
                    navigator.navigateToEdit(event.taskId)
                }
            }
        }
    }

    val taskGroupData = listOf(
        TaskGroupData(
            title = "Overdue",
            tasks = uiState.overdueTasks
        ),
        TaskGroupData(
            title = "Today",
            tasks = uiState.todayTasks
        ),
        TaskGroupData(
            title = "This week",
            tasks = uiState.weekTasks
        ),
        TaskGroupData(
            title = "Later",
            tasks = uiState.laterTasks
        ),
        TaskGroupData(
            title = "No date",
            tasks = uiState.noDateTasks
        ),
        TaskGroupData(
            title = "Completed",
            tasks = uiState.completedTasks
        )
    )

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
            onRefresh = { viewModel.onEvent(TaskEvent.RefreshedTasks) }
        ) {
            TaskGroups(
                modifier = Modifier.padding(15.dp),
                groups = taskGroupData,
                onSingleTaskClick = { viewModel.onEvent(TaskEvent.OpenedTask(it)) },
                onSingleTaskCheckedChanged = { check, _, taskId ->
                    viewModel.onEvent(TaskEvent.ChangedTaskStatus(taskId, check))
                },
                onTaskDelete = {
                    viewModel.onEvent(TaskEvent.DeletedTask(it))
                },
                onErrorOccurred = { viewModel.onEvent(TaskEvent.OccurredError(it)) },
                placeholderContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 130.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "All tasks are done!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                        Icon(
                            painter = painterResource(id = TaskOasisIcons.smileFace),
                            contentDescription = "Smiling face illustration",
                            tint = MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(180.dp, 180.dp)
                        )
                    }
                }
            )
        }
    }
}

