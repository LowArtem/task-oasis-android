package com.trialbot.core_uicomponents.components.task

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.disabledColor
import com.trialbot.core_model.TaskShortDto
import com.trialbot.core_model.constants.Constants.EXPANDING_TRANSITION_DURATION
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_uicomponents.components.LoadingIndicator
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.core_utils.toStringShortFormatted
import java.time.Instant

data class TaskGroupData(
    val title: String,
    val tasks: Result<List<TaskShortDto>>,
    val isVisibleWhenEmpty: Boolean = false,
    val isMutable: Boolean = false,
    val areItemsExpandable: Boolean = false,
    val isExpandedByDefault: Boolean = false
)

@Composable
fun TaskGroups(
    modifier: Modifier = Modifier,
    groups: List<TaskGroupData>,
    onSingleTaskClick: (taskId: Int, groupIndex: Int) -> Unit,
    onSingleTaskCheckedChanged: (check: Boolean, groupIndex: Int, taskId: Int) -> Unit,
    onTaskDelete: (taskId: Int) -> Unit,
    onErrorOccurred: (Throwable) -> Unit,
    onNewItemAddedCallback: (groupIndex: Int, text: String) -> Unit,
    onItemEditedCallback: (groupIndex: Int, taskId: Int, newText: String) -> Unit,
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
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        ) + fadeOut(
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        )
    }

    val expandedStates = remember(groups) {
        groups.map { it.isExpandedByDefault }.toMutableStateList()
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

                    if (group.tasks.data.isNotEmpty() || group.isVisibleWhenEmpty) {

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

                        items (
                            items = group.tasks.data,
                            key = {task -> "group_${i}_${task.id}" }
                        ) { task ->
                            AnimatedVisibility(
                                visible = group.tasks.data.isNotEmpty() && expanded,
                                enter = enterTransition,
                                exit = exitTransition
                            ) {
                                val isItemAdding = remember { mutableStateOf(false) }


                                TaskItem(
                                    text = task.name,
                                    onClick = {
                                        if (group.isMutable) {
                                            isItemAdding.value = true
                                        } else {
                                            onSingleTaskClick(task.id, i)
                                        }
                                    },
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
                                        ?.toStringShortFormatted(),
                                    hasNotification = task.hasNotification,
                                    hasRepeat = task.hasRepeat,
                                    modifier = Modifier,
                                    isExpandable = group.areItemsExpandable,
                                    isAddingItem = isItemAdding.value,
                                    defaultAddingItemState = true,
                                    onItemAdded = {
                                        onItemEditedCallback(i, task.id, it)
                                        isItemAdding.value = false
                                    }
                                )
                            }
                        }

                        if (group.isMutable) {
                            item {
                                val text = remember { mutableStateOf("") }

                                AnimatedVisibility(
                                    visible = expanded,
                                    enter = enterTransition,
                                    exit = exitTransition
                                ) {
                                    TaskItem(
                                        text = text.value,
                                        onClick = {},
                                        onCheckedChanged = {},
                                        onSwipeToDelete = {},
                                        isAddingItem = true,
                                        onItemAdded = {
                                            onNewItemAddedCallback(i, it)
                                        },
                                        isExpandable = false
                                    )
                                }
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
private fun TaskGroupHeader(
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


@Preview(showBackground = true, device = "id:pixel_3_xl")
@Composable
private fun TaskGroupPreview() {

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
    ).toMutableStateList()

    val mutableTasks = mutableListOf<TaskShortDto>().toMutableStateList()

    val taskGroupData = mutableListOf(
        TaskGroupData(
            title = "Today",
            tasks = Result.Success(tasks),
            isExpandedByDefault = true
        ),
        TaskGroupData(
            title = "This week",
            tasks = Result.Success(tasks.map { it.copy() })
        ),
        TaskGroupData(
            title = "Later",
            tasks = Result.Success(tasks.map { it.copy() })
        ),
        TaskGroupData(
            title = "Subtasks",
            tasks = Result.Success(mutableTasks),
            isVisibleWhenEmpty = true,
            isMutable = true,
            areItemsExpandable = true,
            isExpandedByDefault = true
        )
    ).toMutableStateList()

    TaskOasisTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            TaskGroups(
                groups = taskGroupData,
                onSingleTaskClick = { _, _ ->

                },
                onSingleTaskCheckedChanged = { _, _, _ -> },
                onTaskDelete = { },
                onErrorOccurred = {},
                onNewItemAddedCallback = { _, text ->
                    mutableTasks.add(
                        TaskShortDto(
                            name = text,
                            deadline = null,
                            status = false,
                            difficulty = Difficulty.EASY,
                            priority = Priority.LOW,
                            hasNotification = false,
                            hasRepeat = false,
                            completionDate = null,
                            id = if (mutableTasks.size == 0) 1 else mutableTasks.last().id + 1
                        )
                    )
                },
                onItemEditedCallback = { _, taskId, newText ->
                    val itemIndex = mutableTasks.indexOf(mutableTasks.first { it.id == taskId })
                    mutableTasks[itemIndex] = mutableTasks[itemIndex].copy(
                        name = newText
                    )
                },
                modifier = Modifier.padding(15.dp),
            )
        }
    }
}