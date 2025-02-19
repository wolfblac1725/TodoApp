package com.juandgaines.todoapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp


@Composable
fun IconExample(
    modifier: Modifier = Modifier,
    icon: IconContainer
) {
    Icon(
        imageVector = icon.icon,
        contentDescription = null,
        modifier = modifier
            .size(48.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RectangleShape
            ).padding(8.dp)
    )
}

@Preview
@Composable
private fun IconExamplePreview(
    @PreviewParameter(IconProvider::class) icon: IconContainer
) {
    IconExample(
        icon = icon,
        modifier = Modifier

    )
}