package com.juandgaines.todoapp.presentation.screens.detail

sealed interface EventTask {
    data object TaskCreated: EventTask
}