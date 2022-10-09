package com.trialbot.feature_tasks.di

import com.trialbot.feature_tasks.data.remote.TaskDao
import com.trialbot.feature_tasks.data.repository.TaskAddEditRepositoryImpl
import com.trialbot.feature_tasks.data.repository.TaskRepositoryImpl
import com.trialbot.feature_tasks.domain.repository.TaskAddEditRepository
import com.trialbot.feature_tasks.domain.repository.TaskRepository
import com.trialbot.feature_tasks.domain.use_case.AddEditTaskUseCase
import com.trialbot.feature_tasks.domain.use_case.GetTasksUseCase
import com.trialbot.feature_tasks.presentation.viewmodels.AddEditTaskViewModel
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

    single<TaskAddEditRepository> {
        TaskAddEditRepositoryImpl(
            taskDao = get()
        )
    }

    factory {
        GetTasksUseCase(
            taskRepository = get(),
            getCurrentTimeInstant = get()
        )
    }

    factory {
        AddEditTaskUseCase(
            taskRepository = get()
        )
    }

    viewModel {
        ListTabViewModel(
            getTasksUseCase = get(),
            addEditTaskUseCase = get()
        )
    }

    viewModel { params ->
        AddEditTaskViewModel(
            addEditTaskUseCase = get(),
            navBackStackEntry = params.get()
        )
    }
}