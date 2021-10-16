package com.example.jetnote.ui.component
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NoteColor(color: Color, size: Dp, border: Dp) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(Color.Green, CircleShape)
    )
}
