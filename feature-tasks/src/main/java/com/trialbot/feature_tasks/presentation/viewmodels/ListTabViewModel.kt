package com.trialbot.feature_tasks.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trialbot.core_model.TaskShortDto
import com.trialbot.core_utils.Result
import com.trialbot.feature_tasks.domain.use_case.AddEditTaskUseCase
import com.trialbot.feature_tasks.domain.use_case.GetTasksUseCase
import com.trialbot.feature_tasks.presentation.events.TaskAddEditEvent
import com.trialbot.feature_tasks.presentation.events.TaskEvent
import com.trialbot.feature_tasks.presentation.events.UiEvent
import com.trialbot.feature_tasks.presentation.state.ListTabUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListTabViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addEditTaskUseCase: AddEditTaskUseCase
) : ViewModel() {

    private val _completedTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getCompletedTasks()
    private val _overdueTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getOverdueTasks()
    private val _todayTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getTodayTasks()
    private val _weekTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getWeekTasks()
    private val _laterTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getLaterTasks()
    private val _noDateTasks: Flow<Result<List<TaskShortDto>>> = getTasksUseCase.getNoDateTasks()

    private val _isRefreshing = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<ListTabUiState> = combine(
        _completedTasks,
        _overdueTasks,
        _todayTasks,
        _weekTasks,
        _laterTasks,
        _noDateTasks,
        _isRefreshing,
        _isError
    ) {
        ListTabUiState(
            completedTasks = it[0] as Result<List<TaskShortDto>>,
            overdueTasks = it[1] as Result<List<TaskShortDto>>,
            todayTasks = it[2] as Result<List<TaskShortDto>>,
            weekTasks = it[3] as Result<List<TaskShortDto>>,
            laterTasks = it[4] as Result<List<TaskShortDto>>,
            noDateTasks = it[5] as Result<List<TaskShortDto>>,
            isRefreshing = it[6] as Boolean,
            isError = it[7] as Boolean
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListTabUiState(
                completedTasks = Result.Loading,
                overdueTasks = Result.Loading,
                todayTasks = Result.Loading,
                weekTasks = Result.Loading,
                laterTasks = Result.Loading,
                noDateTasks = Result.Loading,
                isRefreshing = false,
                isError = false
            )
        )

    private val _uiEventsFlow = MutableSharedFlow<UiEvent>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        viewModelScope.launch {
            _uiEventsFlow.emit(
                UiEvent.ShowShackbar(
                    throwable.message ?: "Unknown error occurred"
                )
            )
        }
    }


    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.ChangedTaskStatus -> {
                // TODO
            }
            is TaskEvent.OpenedTask -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    _uiEventsFlow.emit(UiEvent.NavigateToTaskEdit(event.taskId))
                }
            }
            is TaskEvent.DeletedTask -> {
                // TODO
            }
            is TaskEvent.RefreshedTasks -> {
                // TODO
            }
            is TaskEvent.OccurredError -> {
                // TODO
            }
        }
    }
}