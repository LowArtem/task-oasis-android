package com.trialbot.feature_tasks.domain.use_case

import com.trialbot.core_model.Task
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.asResult
import com.trialbot.feature_tasks.data.model.TaskStatusReceive
import com.trialbot.feature_tasks.data.model.toTaskReceive
import com.trialbot.feature_tasks.data.model.toTaskUpdateReceive
import com.trialbot.feature_tasks.domain.repository.TaskAddEditRepository
import kotlinx.coroutines.flow.Flow

class AddEditTaskUseCase(
    private val taskRepository: TaskAddEditRepository,
) {

    fun getSingleTask(id: Int): Flow<Result<Task>> {
        require(id > 0) { "Id must be greater than zero" }

        return taskRepository.getSingleTask(id).asResult()
    }

    fun addNewTask(task: Task): Flow<Result<Task>> {
        return taskRepository.addNewTask(task.toTaskReceive()).asResult()
    }

    fun updateTask(task: Task): Flow<Result<Task>> {
        require((task.id ?: -1) > 0) { "Id must be greater than zero" }

        return taskRepository.updateTask(task.toTaskUpdateReceive()).asResult()
    }

    fun updateTaskStatus(taskId: Int, newStatus: Boolean): Flow<Result<Boolean>> {
        require(taskId > 0) { "Id must be greater than zero" }

        return taskRepository.updateTaskStatus(TaskStatusReceive(taskId, newStatus)).asResult()
    }

    fun updateSubtaskStatus(subtaskId: Int, newStatus: Boolean): Flow<Result<Boolean>> {
        require(subtaskId > 0) { "Id must be greater than zero" }

        return taskRepository.updateSubtaskStatus(TaskStatusReceive(subtaskId, newStatus)).asResult()
    }

    fun deleteTask(taskId: Int): Flow<Result<Boolean>> {
        require(taskId > 0) { "Id must be greater than zero" }

        return taskRepository.deleteTask(taskId).asResult()
    }

    fun deleteSubtask(subtaskId: Int): Flow<Result<Boolean>> {
        require(subtaskId > 0) { "Id must be greater than zero" }

        return taskRepository.deleteSubtask(subtaskId).asResult()
    }
}