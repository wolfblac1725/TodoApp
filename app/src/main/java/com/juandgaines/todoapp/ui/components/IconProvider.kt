package com.juandgaines.todoapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class IconProvider: PreviewParameterProvider<IconContainer>{
    override val values: Sequence<IconContainer>
        get() = sequenceOf(
            IconContainer(Icons.Default.Favorite),
            IconContainer(Icons.Default.Call),
            IconContainer(Icons.Default.Home),
            IconContainer(Icons.Default.Star),
        )
}