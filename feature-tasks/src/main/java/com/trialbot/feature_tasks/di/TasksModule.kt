package com.trialbot.feature_tasks.di

import com.trialbot.feature_tasks.data.remote.TaskDao
import com.trialbot.feature_tasks.data.repository.TaskRepositoryImpl
import com.trialbot.feature_tasks.domain.repository.TaskRepository
import com.trialbot.feature_tasks.domain.use_case.GetTasksUseCase
import com.trialbot.feature_tasks.presentation.viewmodels.ListTabViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val tasksModule = module {

    single<TaskDao> {
        val retrofit: Retrofit by inject()
        retrofit.create(TaskDao::class.java)
    }

    single<TaskRepository> {
        TaskRepositoryImpl(
            taskDao = get()
        )
    }

    factory {
        GetTasksUseCase(
            taskRepository = get(),
            getCurrentTimeInstant = get()
        )
    }

    viewModel {
        ListTabViewModel(
            getTasksUseCase = get()
        )
    }
}