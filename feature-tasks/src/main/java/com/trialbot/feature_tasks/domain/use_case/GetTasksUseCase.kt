package com.trialbot.feature_tasks.domain.use_case

import com.trialbot.core_utils.GetCurrentTimeInstant
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.asResult
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.feature_tasks.data.model.TaskShortDto
import com.trialbot.feature_tasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import java.util.*

class GetTasksUseCase(
    private val taskRepository: TaskRepository,
    private val getCurrentTimeInstant: GetCurrentTimeInstant
) {

    private val uncompletedTasks = getUncompletedTasks()

    fun getCompletedTasks(): Flow<Result<TaskShortDto>> {
        return taskRepository.getCompletedTasks().asResult()
    }

    fun getOverdueTasks(): Flow<Result<TaskShortDto>> {
        val current = getCurrentTimeInstant.getCurrentTime()

        return uncompletedTasks.filter {
            (it.deadline ?: current) < current
        }.asResult()
    }

    fun getTodayTasks(): Flow<Result<TaskShortDto>> {
        val current = getCurrentTimeInstant.getCurrentTime().toLocalDateTimeCurrentZone()

        return uncompletedTasks.filter {
            it.deadline?.toLocalDateTimeCurrentZone()?.dayOfMonth == current.dayOfMonth &&
            it.deadline.toLocalDateTimeCurrentZone().month == current.month &&
            it.deadline.toLocalDateTimeCurrentZone().year == current.year
        }.asResult()
    }

    fun getWeekTasks(): Flow<Result<TaskShortDto>> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val monday = calendar.time
        val nextMonday = Date(monday.time + 7*24*60*60*1000)

        return uncompletedTasks.filter {
            it.deadline?.isAfter(monday.toInstant()) ?: false && it.deadline?.isBefore(nextMonday.toInstant()) ?: false
                    && it.deadline?.toLocalDateTimeCurrentZone()?.dayOfMonth != getCurrentTimeInstant.getCurrentTime().toLocalDateTimeCurrentZone().dayOfMonth
        }.asResult()
    }

    fun getLaterTasks(): Flow<Result<TaskShortDto>> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val monday = calendar.time
        val nextMonday = Date(monday.time + 7*24*60*60*1000 - 1000)

        return uncompletedTasks.filter {
            it.deadline?.isAfter(nextMonday.toInstant()) ?: false
        }.asResult()
    }

    fun getNoDateTasks(): Flow<Result<TaskShortDto>> {
        return uncompletedTasks.filter { it.deadline == null }.asResult()
    }

    private fun getUncompletedTasks(): Flow<TaskShortDto> {
        return taskRepository.getUncompletedTasks()
    }
}