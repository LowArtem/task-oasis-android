package com.trialbot.feature_tasks.domain.repository

import com.trialbot.core_model.Task
import com.trialbot.feature_tasks.data.model.TaskReceive
import com.trialbot.feature_tasks.data.model.TaskStatusReceive
import com.trialbot.feature_tasks.data.model.TaskUpdateReceive
import kotlinx.coroutines.flow.Flow

interface TaskAddEditRepository {

    fun getSingleTask(id: Int): Flow<Task>

    fun addNewTask(task: TaskReceive): Flow<Task>

    fun updateTask(task: TaskUpdateReceive): Flow<Task>

    fun updateTaskStatus(taskStatus: TaskStatusReceive): Flow<Boolean>

    fun updateSubtaskStatus(subtaskStatus: TaskStatusReceive): Flow<Boolean>

    fun deleteTask(id: Int): Flow<Boolean>

    fun deleteSubtask(id: Int): Flow<Boolean>
}