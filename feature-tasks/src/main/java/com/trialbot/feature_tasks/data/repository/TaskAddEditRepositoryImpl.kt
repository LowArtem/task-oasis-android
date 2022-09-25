package com.trialbot.feature_tasks.data.repository

import com.trialbot.core_model.Task
import com.trialbot.core_utils.bodyOrThrow
import com.trialbot.core_utils.withRetry
import com.trialbot.feature_tasks.data.model.*
import com.trialbot.feature_tasks.data.remote.TaskDao
import com.trialbot.feature_tasks.domain.repository.TaskAddEditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskAddEditRepositoryImpl(
    private val taskDao: TaskDao
) : TaskAddEditRepository {

    override fun getSingleTask(id: Int): Flow<Task> {
        return flow {
            val response = withRetry { taskDao.getSingleTask(id) }
            emit(response.bodyOrThrow().toTask())
        }
    }

    override fun addNewTask(task: TaskReceive): Flow<Task> {
        return flow {
            val response = withRetry { taskDao.addNewTask(task) }
            emit(response.bodyOrThrow().toTask())
        }
    }

    override fun updateTask(task: TaskUpdateReceive): Flow<Task> {
        return flow {
            val response = withRetry { taskDao.updateTask(task) }
            emit(response.bodyOrThrow().toTask())
        }
    }

    override fun updateTaskStatus(taskStatus: TaskStatusReceive): Flow<Boolean> {
        return flow {
            val response = withRetry { taskDao.updateTaskStatus(taskStatus) }
            emit(response.bodyOrThrow().isNotBlank())
        }
    }

    override fun updateSubtaskStatus(subtaskStatus: TaskStatusReceive): Flow<Boolean> {
        return flow {
            val response = withRetry { taskDao.updateSubtaskStatus(subtaskStatus) }
            emit(response.bodyOrThrow().isNotBlank())
        }
    }

    override fun deleteTask(id: Int): Flow<Boolean> {
        return flow {
            val response = withRetry { taskDao.deleteTask(id) }
            emit(response.bodyOrThrow().isNotBlank())
        }
    }

    override fun deleteSubtask(id: Int): Flow<Boolean> {
        return flow {
            val response = withRetry { taskDao.deleteSubtask(id) }
            emit(response.bodyOrThrow().isNotBlank())
        }
    }

}