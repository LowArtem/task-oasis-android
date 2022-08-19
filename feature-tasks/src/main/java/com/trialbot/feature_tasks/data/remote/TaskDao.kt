package com.trialbot.feature_tasks.data.remote

import com.trialbot.feature_tasks.data.model.TaskShortResponse
import retrofit2.Response
import retrofit2.http.GET

interface TaskDao {

    @GET("./tasks/completed")
    suspend fun getCompletedTasks(): Response<List<TaskShortResponse>>

    @GET("./tasks/uncompleted")
    suspend fun getUncompletedTasks(): Response<List<TaskShortResponse>>
}