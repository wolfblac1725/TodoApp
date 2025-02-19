package com.juandgaines.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juandgaines.todoapp.presentation.screens.detail.TaskScreenRoot
import com.juandgaines.todoapp.presentation.screens.detail.TaskViewModel
import com.juandgaines.todoapp.presentation.screens.home.HomeScreenRoot
import com.juandgaines.todoapp.presentation.screens.home.HomeScreenViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    Box(
       modifier = Modifier.fillMaxSize()
    ){
        NavHost(
            navController = navController,
            startDestination = HomeScreenDes
        ){
            composable<HomeScreenDes> {
                val viewModel: HomeScreenViewModel = hiltViewModel()
                HomeScreenRoot(
                    navigateToTaskScreen = {
                        navController.navigate(TaskScreenDes(it))
                    },
                    viewModel = viewModel
                )
            }
            composable<TaskScreenDes> {
                val viewModel: TaskViewModel = hiltViewModel()
                TaskScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}
@Serializable
object HomeScreenDes

@Serializable
data class TaskScreenDes(
    val taskId: String? = null
)