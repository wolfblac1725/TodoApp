package com.juandgaines.todoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.R
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

@Composable
fun SummaryInfo (
    modifier: Modifier,
    date: String = "Enero 7, 2025",
    taskSummary: String = "10",
    completedTask: Int,
    totalTask: Int
){
    val angleRatio = remember{
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = completedTask, key2 = totalTask){
        angleRatio.animateTo(
            targetValue = if(totalTask == 0) 0f else (completedTask.toFloat() / totalTask.toFloat()),
            animationSpec = tween(
                durationMillis = 800
            )
        )
    }
    Row (
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column( modifier.padding(8.dp).weight(1f)){
            Text(
                text = date,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.summary_info, taskSummary),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,

                )

        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(start = 16.dp).aspectRatio(1f).weight(1f)
        ){
            val colorBase = MaterialTheme.colorScheme.inversePrimary
            val progress = MaterialTheme.colorScheme.primary
            val strokeWidth = 16.dp
            Canvas(
                modifier = Modifier.aspectRatio(1f)
            ){
                drawArc(
                    color = colorBase,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                if(completedTask<= totalTask){
                    drawArc(
                        color = progress,
                        startAngle = 90f,
                        sweepAngle = 360f * angleRatio.value,
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
            Text(
                text = "${(completedTask.toFloat()/totalTask.toFloat()).times(100).toInt()}%",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryInfoPreviewLight() {
    TodoAppTheme {
        SummaryInfo(
            modifier = Modifier,
            completedTask = 4,
            totalTask = 10
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SummaryInfoPreviewDark() {
    TodoAppTheme {
        SummaryInfo(modifier = Modifier,
            completedTask = 4,
            totalTask = 10
        )
    }
}