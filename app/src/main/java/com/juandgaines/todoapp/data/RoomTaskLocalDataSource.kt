package com.juandgaines.todoapp.data

import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomTaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskLocalDataSource {
    override val tasksFlow: Flow<List<Task>>
        get() = taskDao.getAllTasks().map {
            it.map { taskEntity -> taskEntity.toTask() }
        }.flowOn(dispatcher)
    override suspend fun addTask(task: Task)  = withContext(dispatcher) {
        taskDao.upsertTask(TaskEntity.fromTask(task))
    }

    override suspend fun updateTask(updatedTask: Task)  = withContext(dispatcher) {
        taskDao.upsertTask(TaskEntity.fromTask(updatedTask))
    }

    override suspend fun removeTask(task: Task) = withContext(dispatcher) {
        taskDao.deleteTaskById(task.id)
    }

    override suspend fun deleteAllTask() = withContext(dispatcher) {
        taskDao.deleteAllTasks()
    }

    override suspend fun getTaskById(taskId: String): Task?  = withContext(dispatcher) {
        taskDao.getTaskById(taskId)?.toTask()
    }

}