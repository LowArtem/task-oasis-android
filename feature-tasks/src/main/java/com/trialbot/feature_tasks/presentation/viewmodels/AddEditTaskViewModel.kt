package com.trialbot.feature_tasks.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.trialbot.core_model.Task
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.NotificationInterval
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_model.enum.RepeatingInterval
import com.trialbot.core_utils.Result
import com.trialbot.feature_tasks.domain.use_case.AddEditTaskUseCase
import com.trialbot.feature_tasks.presentation.events.TaskAddEditEvent
import com.trialbot.feature_tasks.presentation.events.TaskAddEditUiEvent
import com.trialbot.feature_tasks.presentation.state.AddEditUiState
import com.trialbot.feature_tasks.presentation.state.InputFieldState
import com.trialbot.feature_tasks.presentation.ui.screens.destinations.AddEditTaskScreenDestination
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AddEditTaskScreenTitle {
    AddTaskTitle,
    EditTaskTitle
}

class AddEditTaskViewModel(
    private val addEditTaskUseCase: AddEditTaskUseCase,
    navBackStackEntry: NavBackStackEntry
) : ViewModel() {

    private val _args = AddEditTaskScreenDestination.argsFrom(navBackStackEntry)

    private val _task: Flow<Result<Task>>
    private val _screenTitle = MutableStateFlow(AddEditTaskScreenTitle.AddTaskTitle)


    private val _isError = MutableStateFlow(false)

    private val _title = mutableStateOf(InputFieldState())
    val title: State<InputFieldState> = _title

    private val _description = mutableStateOf(InputFieldState())
    val description: State<InputFieldState> = _description

    init {
        val taskId = _args.taskId
        if (taskId < 1) {
            val newTaskDate = _args.newTaskDate

            _task = flowOf(
                Result.Success(
                    Task(
                        name = "",
                        description = "",
                        deadline = newTaskDate,
                        status = false,
                        difficulty = Difficulty.NORMAL,
                        priority = Priority.LOW,
                        repeatingInterval = RepeatingInterval.NONE,
                        notification = NotificationInterval.NONE,
                        completionDate = null,
                        id = null,
                        subtasks = setOf()
                    )
                )
            )
            _title.value = _title.value.copy(text = "")
            _description.value = _description.value.copy(text = "")
        } else {
            _task = addEditTaskUseCase.getSingleTask(taskId)

            viewModelScope.launch {
                _screenTitle.emit(AddEditTaskScreenTitle.EditTaskTitle)

                _task.collect() {
                    _title.value =
                        _title.value.copy(text = if (it is Result.Success) it.data.name else "")

                    _description.value =
                        _title.value.copy(
                            text = if (it is Result.Success) it.data.description ?: "" else ""
                        )
                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<AddEditUiState> = combine(
        _screenTitle,
        _task,
        _isError
    ) { states: Array<Any> ->
        AddEditUiState(
            screenTitle = states[0] as AddEditTaskScreenTitle,
            task = states[1] as Result<Task>,
            isError = states[2] as Boolean
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddEditUiState(
                screenTitle = AddEditTaskScreenTitle.AddTaskTitle,
                task = Result.Loading,
                isError = false
            )
        )


    private val _uiEventsFlow = MutableSharedFlow<TaskAddEditUiEvent>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        viewModelScope.launch {
            _uiEventsFlow.emit(
                TaskAddEditUiEvent.ShowShackbar(
                    throwable.message ?: "Unknown error occurred"
                )
            )
        }
    }


    fun onEvent(event: TaskAddEditEvent) {
        when (event) {
            is TaskAddEditEvent.DeadlineDateChanged -> {
                TODO()
            }
            is TaskAddEditEvent.DeadlineTimeChanged -> {
                TODO()
            }
            is TaskAddEditEvent.DifficultyChanged -> {
                TODO()
            }
            is TaskAddEditEvent.EnteredDescription -> {
                TODO()
            }
            is TaskAddEditEvent.EnteredTitle -> {
                TODO()
            }
            is TaskAddEditEvent.NotificationTypeChanged -> {
                TODO()
            }
            is TaskAddEditEvent.OccurredError -> {
                TODO()
            }
            is TaskAddEditEvent.OnBackPressed -> {
                TODO()
            }
            is TaskAddEditEvent.PriorityChanged -> {
                TODO()
            }
            is TaskAddEditEvent.RepeatTypeChanged -> {
                TODO()
            }
            is TaskAddEditEvent.SubtaskAdded -> {
                TODO()
            }
            is TaskAddEditEvent.SubtaskDeleted -> {
                TODO()
            }
            is TaskAddEditEvent.SubtaskEdited -> {
                TODO()
            }
        }
    }
}