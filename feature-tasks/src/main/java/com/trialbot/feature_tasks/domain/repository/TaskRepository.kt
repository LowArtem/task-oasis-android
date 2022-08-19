package com.trialbot.feature_tasks.domain.repository

import com.trialbot.feature_tasks.data.model.TaskShortDto
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getCompletedTasks(): Flow<List<TaskShortDto>>

    fun getUncompletedTasks(): Flow<List<TaskShortDto>>
}