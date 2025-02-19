package com.juandgaines.todoapp.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()
}