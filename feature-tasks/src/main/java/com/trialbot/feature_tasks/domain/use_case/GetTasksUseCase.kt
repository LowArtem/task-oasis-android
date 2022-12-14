package com.trialbot.feature_tasks.domain.use_case

import com.trialbot.core_model.TaskShortDto
import com.trialbot.core_utils.GetCurrentTimeInstant
import com.trialbot.core_utils.Result
import com.trialbot.core_utils.asResult
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.feature_tasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.util.*

class GetTasksUseCase(
    private val taskRepository: TaskRepository,
    private val getCurrentTimeInstant: GetCurrentTimeInstant
) {

    private var uncompletedTasks: Flow<List<TaskShortDto>>

    init {
        uncompletedTasks = getUncompletedTasks()
    }

    fun getCompletedTasks(): Flow<Result<List<TaskShortDto>>> {
        return taskRepository.getCompletedTasks()
            .transform {
                emit(
                    it.sortedByDescending { task -> task.completionDate }
                )
            }
            .asResult()
    }

    fun getOverdueTasks(): Flow<Result<List<TaskShortDto>>> {
        val current = getCurrentTimeInstant.getCurrentTime()

        return uncompletedTasks.transform {
            emit(
                it.filter { task -> (task.deadline ?: current) < current }
            )
        }.asResult()
    }

    fun getTodayTasks(): Flow<Result<List<TaskShortDto>>> {
        val current = getCurrentTimeInstant.getCurrentTime().toLocalDateTimeCurrentZone()

        return uncompletedTasks.transform {
            emit(
                it.filter { task ->
                    task.deadline?.toLocalDateTimeCurrentZone()?.dayOfMonth == current.dayOfMonth &&
                            task.deadline?.toLocalDateTimeCurrentZone()?.month == current.month &&
                            task.deadline?.toLocalDateTimeCurrentZone()?.year == current.year &&
                            (task.deadline?.toLocalDateTimeCurrentZone()?.hour ?: current.hour) >= current.hour &&
                            (task.deadline?.toLocalDateTimeCurrentZone()?.minute ?: current.minute) >= current.minute
                }
            )
        }.asResult()
    }

    fun getWeekTasks(): Flow<Result<List<TaskShortDto>>> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val monday = calendar.time
        val nextMonday = Date(monday.time + 7 * 24 * 60 * 60 * 1000)

        return uncompletedTasks.transform {
            emit(
                it.filter { task ->
                    task.deadline?.isAfter(monday.toInstant()) ?: false &&
                            task.deadline?.isBefore(nextMonday.toInstant()) ?: false &&
                            task.deadline?.toLocalDateTimeCurrentZone()?.dayOfMonth != getCurrentTimeInstant.getCurrentTime().toLocalDateTimeCurrentZone().dayOfMonth
                }
            )
        }.asResult()
    }

    fun getLaterTasks(): Flow<Result<List<TaskShortDto>>> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val monday = calendar.time
        val nextMonday = Date(monday.time + 7 * 24 * 60 * 60 * 1000 - 1000)

        return uncompletedTasks.transform {
            emit(
                it.filter { task ->
                    task.deadline?.isAfter(nextMonday.toInstant()) ?: false
                }
            )
        }.asResult()
    }

    fun getNoDateTasks(): Flow<Result<List<TaskShortDto>>> {
        return uncompletedTasks.transform {
            emit(
                it.filter { task -> task.deadline == null }
            )
        }.asResult()
    }

    private fun getUncompletedTasks(): Flow<List<TaskShortDto>> {
        return taskRepository.getUncompletedTasks()
    }
}