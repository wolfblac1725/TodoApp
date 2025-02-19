package com.juandgaines.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juandgaines.todoapp.domain.Category
import com.juandgaines.todoapp.domain.Task
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String?,
    val category: Int?,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,
    val date: Long
){

    fun toTask(): Task{
        return Task(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            category = category?.let { Category.fromOrdinal(category) },
            date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date),
                ZoneId.systemDefault()
            )
        )
    }

    companion object{
        fun fromTask(task: Task): TaskEntity{
            return TaskEntity(
                id = task.id,
                title = task.title,
                description = task.description,
                category = task.category?.ordinal,
                isCompleted = task.isCompleted,
                date = task.date
                    .atZone(
                        ZoneId.systemDefault()
                    ).toInstant().toEpochMilli()
            )
        }
    }
}