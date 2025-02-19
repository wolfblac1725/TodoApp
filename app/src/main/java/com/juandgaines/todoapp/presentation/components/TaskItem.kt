package com.juandgaines.todoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.domain.Category
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    onClickItem: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onToggleCompletion: (Task) -> Unit,
    task: Task,
) {
    Row (
        modifier = modifier
            .clickable {
                onClickItem(task.id)
            }
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onToggleCompletion(task)
            }

        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 8.dp).weight(1f)
        ){
            Text(
                text = task.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            if(!task.isCompleted){
                task.description?.let {
                    Text(
                        text = it,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                task.category?.let {
                    Text(
                        text = it.toString().lowercase().substring(0,1).uppercase() + it.toString().substring(1).lowercase() ,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Box {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onDeleteItem(task.id)
                    }
            )
        }

    }

}

@Preview(
    showBackground = true
)
@Composable
private fun TaskItemPreviewLight() {
    TodoAppTheme {
        TaskItem(
            modifier = Modifier,
            onClickItem = {},
            onDeleteItem = {},
            onToggleCompletion = {},
            task = Task(
                id = "1",
                title = "Task 1",
                description = "Description 1",
                category = Category.PERSONAL,
                isCompleted = false
            )
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TaskItemPreviewDark(

) {
    TodoAppTheme {
        TaskItem(
            modifier = Modifier,
            onClickItem = {},
            onDeleteItem = {},
            onToggleCompletion = {},
            task = Task(
                id = "1",
                title = "Task 1",
                description = "Description 1",
                isCompleted = false
            )
        )
    }

}

