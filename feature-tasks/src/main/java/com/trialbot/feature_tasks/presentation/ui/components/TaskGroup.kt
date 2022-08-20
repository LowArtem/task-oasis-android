package com.trialbot.feature_tasks.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.disabledColor
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.core_utils.toStringFormatted
import com.trialbot.feature_tasks.data.model.TaskShortDto
import java.time.Instant

const val EXPANDING_TRANSITION_DURATION = 200

data class TaskGroup(
    val title: String,
    val tasks: Result<List<TaskShortDto>>
)

@Composable
fun TaskGroups(
    groups: List<TaskGroup>,
    onSingleTaskClick: (taskId: Int) -> Unit,
    onSingleTaskCheckedChanged: (check: Boolean, groupIndex: Int, taskId: Int) -> Unit,
    onTaskDelete: (taskId: Int) -> Unit,
    onErrorOccurred: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
    defaultExpandedList: List<Boolean>? = null,
    onExpandedStateChangedCallback: ((expanded: Boolean, groupIndex: Int) -> Unit)? = null,
    placeholderContent: (@Composable () -> Unit)? = null
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        )
    }

    val expandedStates = remember(groups) {
        if (defaultExpandedList == null) {
            List(groups.size) { false }.toMutableStateList()
        } else {
            require(defaultExpandedList.size == groups.size)
            defaultExpandedList.toMutableStateList()
        }
    }

    var emptyGroupCount = remember(groups) { 0 }
    var isLoadingVisible = remember(groups) { false }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        userScrollEnabled = true
    ) {
        groups.forEachIndexed { i, group ->
            val expanded = expandedStates[i]

            when (group.tasks) {
                is Result.Error -> {
                    isLoadingVisible = false

                    onErrorOccurred(
                        group.tasks.exception ?: RuntimeException("Task result is error")
                    )
                }
                is Result.Loading -> {
                    item {
                        if (!isLoadingVisible) {
                            isLoadingVisible = true
                            LoadingIndicator()
                        }
                    }
                }
                is Result.Success -> {
                    isLoadingVisible = false

                    if (group.tasks.data.isNotEmpty()) {

                        item(key = "header_$i") {
                            TaskGroupHeader(
                                title = group.title,
                                tasksSize = group.tasks.data.size,
                                expandedState = expanded,
                                expandedStateChanged = {
                                    expandedStates[i] = !expanded

                                    if (onExpandedStateChangedCallback != null) {
                                        onExpandedStateChangedCallback(expandedStates[i], i)
                                    }
                                }
                            )
                        }

                        items(group.tasks.data, key = { task -> "group_${i}_${task.id}" }) { task ->
                            AnimatedVisibility(
                                visible = group.tasks.data.isNotEmpty() && expanded,
                                enter = enterTransition,
                                exit = exitTransition
                            ) {
                                TaskItem(
                                    text = task.name,
                                    onClick = { onSingleTaskClick(task.id) },
                                    onCheckedChanged = {
                                        onSingleTaskCheckedChanged(it, i, task.id)
                                    },
                                    onSwipeToDelete = {
                                        onTaskDelete(task.id)
                                    },
                                    isChecked = task.status,
                                    priority = task.priority,
                                    deadline = task.deadline
                                        ?.toLocalDateTimeCurrentZone()
                                        ?.toStringFormatted(),
                                    hasNotification = task.hasNotification,
                                    hasRepeat = task.hasRepeat,
                                    modifier = Modifier
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    } else {
                        emptyGroupCount += 1
                    }
                }
            }
        }

        if (emptyGroupCount == groups.size && placeholderContent != null) {
            item {
                placeholderContent()
            }
        }
    }
}

@Composable
fun TaskGroupHeader(
    title: String,
    modifier: Modifier = Modifier,
    tasksSize: Int,
    expandedState: Boolean,
    expandedStateChanged: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .clickable {
                expandedStateChanged()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = tasksSize.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.disabledColor,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        IconButton(
            onClick = { expandedStateChanged() },
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (expandedState) TaskOasisIcons.moreArrowDownExpanded else TaskOasisIcons.moreArrowRightCollapsed
                ),
                contentDescription = "Task group expand button",
                modifier = Modifier.size(30.dp, 30.dp),
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}


@Preview(showBackground = false, device = "id:pixel_3_xl")
@Composable
fun TaskGroupPreview() {

    val tasks = mutableListOf(
        TaskShortDto(
            name = "Make habit screen",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.IMPORTANT,
            hasNotification = true,
            hasRepeat = false,
            completionDate = null,
            id = 1
        ),
        TaskShortDto(
            name = "Start to code this android app",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.NORMAL,
            hasNotification = false,
            hasRepeat = true,
            completionDate = null,
            id = 2
        ),
        TaskShortDto(
            name = "Create a new habit",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.LOW,
            hasNotification = false,
            hasRepeat = false,
            completionDate = null,
            id = 3
        ),
        TaskShortDto(
            name = "Go to swim",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.LOW,
            hasNotification = true,
            hasRepeat = true,
            completionDate = null,
            id = 4
        )
    )

    val taskGroups = listOf(
        TaskGroup(
            title = "Today",
            tasks = Result.Success(tasks)
        ),
        TaskGroup(
            title = "This week",
            tasks = Result.Success(tasks.map { it.copy() })
        ),
        TaskGroup(
            title = "Later",
            tasks = Result.Success(tasks.map { it.copy() })
        )
    )

    TaskOasisTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TaskGroups(
                groups = taskGroups,
                onSingleTaskClick = {},
                onSingleTaskCheckedChanged = { _, _, _ -> },
                onTaskDelete = { },
                onErrorOccurred = {},
                modifier = Modifier.padding(15.dp),
                defaultExpandedList = listOf(true, false, false)
            )
        }
    }
}