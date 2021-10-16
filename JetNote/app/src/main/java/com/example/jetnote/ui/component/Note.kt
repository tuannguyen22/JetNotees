package com.example.jetnote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetnote.domain.model.NoteModel

@Composable
fun Note(
    note: NoteModel,
    onNoteCheckedChange: (NoteModel) -> Unit,
    onNoteClick: (NoteModel) -> Unit,
) {
    val backgroundShape: Shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, backgroundShape)
            .heightIn(min = 64.dp)
            .background(Color.White, backgroundShape)
    ) {
        NoteColor(
            color = Color.Green, size = 5.dp, border = 1.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        {
            Text(
                text = note.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    letterSpacing = 0.15.sp
                )
            )
            Text(text = note.content)
        }
        Checkbox(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}
