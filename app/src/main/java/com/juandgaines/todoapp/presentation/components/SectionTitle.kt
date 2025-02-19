package com.juandgaines.todoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Box {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier
                .padding(8.dp)
        )
    }
    
}

@Preview(
    showBackground = true
)
@Composable
private fun SectionTitlePreviewLigth() {
    TodoAppTheme {
        SectionTitle(title = "Section Title")
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SectionTitlePreviewDart() {
    TodoAppTheme {
        SectionTitle(title = "Section Title")
    }
}