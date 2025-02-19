package com.juandgaines.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import com.juandgaines.todoapp.navigation.TaskScreenDes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val taskData = savedStateHandle.toRoute<TaskScreenDes>()

    var state by mutableStateOf(TaskScreenState())
    private val eventsChannel = Channel<EventTask>()
    val events = eventsChannel.receiveAsFlow()
    private val canSaveTask = snapshotFlow { state.taskName.text.toString() }

    private var editTask: Task? = null

    init {
        taskData.taskId?.let {
            viewModelScope.launch {
                 val task = taskLocalDataSource.getTaskById(it)
                editTask = task
                state = state.copy(
                    taskName = TextFieldState(task?.title ?: ""),
                    taskDescription = TextFieldState(task?.description?:""),
                    isTaskDone = task?.isCompleted ?: false,
                    category = task?.category
                )
            }
        }
        canSaveTask.onEach {
            state = state.copy(canSaveTask = it.isNotEmpty())
        }.launchIn(viewModelScope)
    }

    fun onAction(action: ActionTask) {
        viewModelScope.launch {
            when(action) {
                is ActionTask.ChangeCategoryTask -> {
                    state = state.copy(category = action.category)
                }
                is ActionTask.ChangeDoneTask -> {
                    state = state.copy(isTaskDone = action.isTaskDone)
                }
                ActionTask.SaveTask -> {
                    editTask?.let {
                        taskLocalDataSource.updateTask(
                            updatedTask = it.copy(
                                id = it.id,
                                title = state.taskName.text.toString(),
                                description = state.taskDescription.text.toString(),
                                isCompleted = state.isTaskDone,
                                category = state.category
                            )
                        )

                    }?:run{
                        val task = Task(
                            id = UUID.randomUUID().toString(),
                            title = state.taskName.text.toString(),
                            description = state.taskDescription.text.toString(),
                            isCompleted = state.isTaskDone,
                            category = state.category
                        )
                        taskLocalDataSource.addTask(task)
                    }
                    eventsChannel.send(EventTask.TaskCreated)
                }
                else -> Unit
            }
        }
    }
}
