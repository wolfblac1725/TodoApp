package com.juandgaines.todoapp.presentation.screens.detail

import com.juandgaines.todoapp.domain.Category

sealed interface ActionTask {
    data object SaveTask: ActionTask
    data object Back: ActionTask
    data class ChangeCategoryTask(val category: Category?): ActionTask
    data class ChangeDoneTask(val isTaskDone: Boolean): ActionTask
}