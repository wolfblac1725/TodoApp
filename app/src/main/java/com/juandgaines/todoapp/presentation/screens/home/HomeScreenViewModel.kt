package com.juandgaines.todoapp.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.juandgaines.todoapp.domain.TaskLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskLocalDataSource : TaskLocalDataSource,
): ViewModel() {
    var state by mutableStateOf(HomeDataState())
        private set
    private val evenChannel = Channel<HomeScreenEvent>()
    val event = evenChannel.receiveAsFlow()

    init {
        state = state.copy(
            date = LocalDate.now().let {
                DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy").format(it)
            }
        )
        taskLocalDataSource.tasksFlow
            .onEach { it ->
                val completedTask = it.filter { it.isCompleted }. sortedByDescending { it.date }
                val pendingTask = it.filter { !it.isCompleted }.sortedByDescending { it.date }
                state = state.copy(
                    summary = pendingTask.size.toString(),
                    completedTask = completedTask,
                    pendingTask = pendingTask
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when (action) {
                HomeScreenAction.OnDeleteAllTask -> {
                    taskLocalDataSource.deleteAllTask()
                    evenChannel.send(HomeScreenEvent.DeleteAllTask)
                }
                is HomeScreenAction.OnDeleteTask -> {
                    taskLocalDataSource.removeTask(action.task)
                    evenChannel.send(HomeScreenEvent.DeleteTask)
                }
                is HomeScreenAction.OnToggleTask -> {
                    val updatedTask = action.task.copy(isCompleted = !action.task.isCompleted)
                    taskLocalDataSource.updateTask(updatedTask)
                    evenChannel.send(HomeScreenEvent.UpdateTask)
                }
                else -> Unit
            }
        }
    }

}