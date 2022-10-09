package com.trialbot.feature_tasks.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.savedstate.SavedStateRegistryOwner
import com.ramcosta.composedestinations.annotation.Destination
import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.attentionColor
import com.trialbot.core_designsystem.ui.theme.goodColor
import com.trialbot.core_model.Task
import com.trialbot.core_model.toTaskShortDto
import com.trialbot.core_uicomponents.components.*
import com.trialbot.core_uicomponents.components.task.TaskGroupData
import com.trialbot.core_uicomponents.components.task.TaskGroups
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.feature_tasks.R
import com.trialbot.feature_tasks.TasksNavigator
import com.trialbot.feature_tasks.presentation.events.TaskAddEditEvent
import com.trialbot.feature_tasks.presentation.events.TaskAddEditUiEvent
import com.trialbot.feature_tasks.presentation.state.AddEditUiState
import com.trialbot.feature_tasks.presentation.viewmodels.AddEditTaskScreenTitle
import com.trialbot.feature_tasks.presentation.viewmodels.AddEditTaskViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder
import java.time.Instant

@Destination
@Composable
fun AddEditTaskScreen(
    navigator: TasksNavigator,
    taskId: Int = -1,
    newTaskDate: Instant? = null,
    savedStateOwner: SavedStateRegistryOwner = LocalSavedStateRegistryOwner.current,
    viewModel: AddEditTaskViewModel = koinViewModel(parameters = {
        ParametersHolder(
            mutableListOf(
                (savedStateOwner as? NavBackStackEntry)
            )
        )
    })
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    val titleState = viewModel.title.value
    val descriptionState = viewModel.description.value

    val uiState: AddEditUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventsFlow.collectLatest { event ->
            when (event) {
                is TaskAddEditUiEvent.ShowShackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is TaskAddEditUiEvent.NavigateBack -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AddEditTaskTopBar(
                title = when (uiState.screenTitle) {
                    AddEditTaskScreenTitle.AddTaskTitle -> stringResource(R.string.add_task_title)
                    AddEditTaskScreenTitle.EditTaskTitle -> stringResource(R.string.edit_task_title)
                },
                onBackPressed = { viewModel.onEvent(TaskAddEditEvent.OnBackPressed) },
                onMenuPressed = { /*TODO*/ }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
//                .verticalScroll(scrollState) // TODO: исправить это (через изменение TaskGroup для отдельного элемента)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            InputHintField(
                value = titleState.text,
                onValueChanged = { viewModel.onEvent(TaskAddEditEvent.EnteredTitle(taskId, it)) },
                hint = stringResource(R.string.enter_title_hint),
                keyboardActions = KeyboardActions.Default,
                isError = titleState.validatingError.isErrorOccurred,
                error = titleState.validatingError.message,
                isLabelAboveVisible = false
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            InputHintField(
                modifier = Modifier
                    .sizeIn(minHeight = 312.dp, maxHeight = 600.dp)
                    .fillMaxWidth(),
                textFieldSizes = TextFieldSizes(minHeight = 312.dp),
                value = descriptionState.text,
                onValueChanged = { viewModel.onEvent(TaskAddEditEvent.EnteredDescription(taskId, it)) },
                hint = stringResource(R.string.enter_description_hint),
                keyboardActions = KeyboardActions.Default,
                isError = false,
                isLabelAboveVisible = false,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(25.dp))

            TaskGroups(
                groups = listOf(TaskGroupData(
                    title = stringResource(R.string.subtasks_group_title),
                    tasks = when (uiState.task) {
                        is Result.Error -> {
                            Result.Error((uiState.task as Result.Error).exception)
                        }
                        is Result.Loading -> {
                            Result.Loading
                        }
                        is Result.Success -> {
                            Result.Success((uiState.task as Result.Success<Task>).data.subtasks.map { it.toTaskShortDto() })
                        }
                    },
                    isVisibleWhenEmpty = true,
                    isMutable = true,
                    areItemsExpandable = true,
                    isExpandedByDefault = true
                )),
                onSingleTaskClick = { _, _ ->

                },
                onSingleTaskCheckedChanged = { _, _, _ -> },
                onTaskDelete = { },
                onErrorOccurred = {},
                onNewItemAddedCallback = { _, text ->

                },
                onItemEditedCallback = { _, taskId, newText ->

                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            TitledLineBlock(
                title = stringResource(R.string.task_deadline_title)
            ) {
                AppDatePicker(
                    startedDate = when (uiState.task) {
                        is Result.Error, Result.Loading -> {
                            Instant.now().toLocalDateTimeCurrentZone()
                        }
                        is Result.Success -> {
                            (uiState.task as Result.Success<Task>).data.deadline?.toLocalDateTimeCurrentZone()
                                ?: Instant.now().toLocalDateTimeCurrentZone()
                        }
                    },
                    onDateChanged = {

                    }
                )
                AppTimePicker(
                    startedTime = when (uiState.task) {
                        is Result.Error, Result.Loading -> {
                            Instant.now().toLocalDateTimeCurrentZone().toLocalTime()
                        }
                        is Result.Success -> {
                            (uiState.task as Result.Success<Task>).data.deadline?.toLocalDateTimeCurrentZone()?.toLocalTime()
                                ?: Instant.now().toLocalDateTimeCurrentZone().toLocalTime()
                        }
                    },
                    onTimeChanged = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            TitledLineBlock(
                title = stringResource(R.string.notification_title)
            ) {
                AppDropDown(
                    items = listOf(
                        AppDropDownItem(
                            text = stringResource(R.string.none_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.in_time_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.five_minutes_before_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.one_hour_before_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.in_day_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.in_day_before_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.in_week_before_title),
                            icon = null
                        )
                    ),
                    icon = TaskOasisIcon.Companion.of(TaskOasisIcons.alarm),
                    onItemChanged = { iconIndex ->

                    }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            TitledLineBlock(
                title = stringResource(R.string.repeat_title)
            ) {
                AppDropDown(
                    items = listOf(
                        AppDropDownItem(
                            text = stringResource(R.string.none_title),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.repeat_weekly),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.repeat_monthly),
                            icon = null
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.repeat_yearly),
                            icon = null
                        )
                    ),
                    icon = TaskOasisIcon.Companion.of(TaskOasisIcons.repeat),
                    onItemChanged = { iconIndex ->

                    }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            TitledLineBlock(
                title = stringResource(R.string.difficulty_priority_title)
            ) {
                AppDropDown(
                    items = listOf(
                        AppDropDownItem(
                            text = stringResource(R.string.diff_easy),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.circleFilled),
                            iconTint = { MaterialTheme.colors.goodColor }
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.diff_normal),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.circleFilled),
                            iconTint = { MaterialTheme.colors.attentionColor }
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.diff_hard),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.circleFilled),
                            iconTint = { MaterialTheme.colors.error }
                        )
                    ),
                    icon = null,
                    onItemChanged = { iconIndex ->

                    }
                )
                AppDropDown(
                    items = listOf(
                        AppDropDownItem(
                            text = stringResource(R.string.priority_low),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.priorityExclamation),
                            iconTint = { MaterialTheme.colors.goodColor }
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.priority_normal),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.priorityExclamation),
                            iconTint = { MaterialTheme.colors.attentionColor }
                        ),
                        AppDropDownItem(
                            text = stringResource(R.string.priority_important),
                            icon = TaskOasisIcon.Companion.of(TaskOasisIcons.priorityExclamation),
                            iconTint = { MaterialTheme.colors.error }
                        )
                    ),
                    icon = null,
                    onItemChanged = { iconIndex ->

                    }
                )
            }
        }
    }
}

@Composable
private fun AddEditTaskTopBar(
    title: String,
    onBackPressed: () -> Unit,
    onMenuPressed: () -> Unit
) {
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
                onClick = onBackPressed
            ) {
                Icon(
                    imageVector = TaskOasisIcons.backArrowLeft,
                    contentDescription = "back",
                    modifier = Modifier.size(35.dp, 35.dp),
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onMenuPressed
            ) {
                Icon(
                    imageVector = TaskOasisIcons.moreDots,
                    contentDescription = "more",
                    modifier = Modifier.size(35.dp, 35.dp),
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        modifier = Modifier.padding(horizontal = 12.dp),
        elevation = 0.dp // TODO: сделать по гайду elevation в момент прокрутки
    )
}


@Preview
@Composable
private fun AddEditTaskTopBarPreview() {
    TaskOasisTheme {

        AddEditTaskTopBar(
            title = "Add a task",
            onBackPressed = { /*TODO*/ },
            onMenuPressed = { /*TODO*/ }
        )
    }
}