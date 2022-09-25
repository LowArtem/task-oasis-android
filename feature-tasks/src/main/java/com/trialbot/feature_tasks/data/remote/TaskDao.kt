package com.trialbot.feature_tasks.data.remote

import com.trialbot.feature_tasks.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskDao {

    @GET("./tasks/completed")
    suspend fun getCompletedTasks(): Response<List<TaskShortResponse>>

    @GET("./tasks/uncompleted")
    suspend fun getUncompletedTasks(): Response<List<TaskShortResponse>>

    @GET("./tasks/{id}")
    suspend fun getSingleTask(@Path("id") id: Int): Response<TaskResponse>

    @POST("./tasks/add")
    suspend fun addNewTask(@Body task: TaskReceive): Response<TaskResponse>

    @PUT("./tasks/update")
    suspend fun updateTask(@Body task: TaskUpdateReceive): Response<TaskResponse>

    @PUT("./tasks/update/status")
    suspend fun updateTaskStatus(@Body taskStatusReceive: TaskStatusReceive): Response<String>

    @PUT("./tasks/update/subtask/status")
    suspend fun updateSubtaskStatus(@Body subtaskStatusReceive: TaskStatusReceive): Response<String>

    @DELETE("./tasks/delete/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<String>

    @DELETE("./tasks/delete/subtask/{id}")
    suspend fun deleteSubtask(@Path("id") id: Int): Response<String>
}