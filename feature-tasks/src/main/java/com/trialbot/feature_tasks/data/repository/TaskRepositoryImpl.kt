package com.trialbot.feature_tasks.data.repository

import com.trialbot.core_utils.bodyOrThrow
import com.trialbot.core_utils.withRetry
import com.trialbot.feature_tasks.data.model.TaskShortDto
import com.trialbot.feature_tasks.data.model.toDto
import com.trialbot.feature_tasks.data.remote.TaskDao
import com.trialbot.feature_tasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getCompletedTasks(): Flow<TaskShortDto> {
        return flow {
            val response = withRetry { taskDao.getCompletedTasks() }
            response.bodyOrThrow().map { it.toDto() }.forEach {
                emit(it)
            }
        }
    }

    override fun getUncompletedTasks(): Flow<TaskShortDto> {
        return flow {
            val response = withRetry { taskDao.getUncompletedTasks() }
            response.bodyOrThrow().map { it.toDto() }.forEach {
                emit(it)
            }
        }
    }
}