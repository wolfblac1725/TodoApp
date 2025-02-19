package com.juandgaines.todoapp.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.juandgaines.todoapp.domain.Category
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.presentation.screens.home.HomeDataState

class HomeScreenPreviewProvider: PreviewParameterProvider<HomeDataState> {
    override val values: Sequence<HomeDataState>
        get() = sequenceOf(
            HomeDataState(
                date = "Enero 7, 2025",
                summary = "${pendingTask.size} Icompletas, ${completedTask.size} Completas",
                completedTask = completedTask,
                pendingTask = pendingTask
            )
        )
}

val completedTask = mutableListOf<Task>()
    .apply {
        repeat(20){
            add(
                Task(
                    id = it.toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.WORK,
                    isCompleted = false
                )
            )
        }
    }

val pendingTask = mutableListOf<Task>()
    .apply {
        repeat(20){
            add(
                Task(
                    id = (it+30).toString(),
                    title = "Task $it",
                    description = "Description $it",
                    category = Category.OTHER,
                    isCompleted = true
                )
            )
        }
    }