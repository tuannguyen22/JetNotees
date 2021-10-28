package com.example.jetnote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.example.jetnote.util.fromHex

@ExperimentalMaterialApi
@Composable
fun Note(
    note: NoteModel,
    onNoteCheckedChange: (NoteModel) -> Unit,
    onNoteClick: (NoteModel) -> Unit,
    isSelected: Boolean
) {
    val background = if (isSelected)
        Color.LightGray
    else
        MaterialTheme.colors.surface
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), backgroundColor = background
    ) {
        ListItem(text = { Text(text = note.title) },
            secondaryText = {
                Text(text = note.content)
            },
            icon = {
                NoteColor(color = Color.fromHex(note.color.hex), size = 40.dp, border = 1.dp)
            },
            trailing = {
                if (note.isCheckedOff != null) {
                    Checkbox(checked = note.isCheckedOff, onCheckedChange = { isSelected ->
                        val newNote = note.copy(isCheckedOff = isSelected)
                        onNoteCheckedChange.invoke(newNote)
                    }
                    )
                }

            },
            modifier = Modifier.clickable { onNoteClick.invoke(note) }
        )
    }

//    val backgroundShape: Shape = RoundedCornerShape(4.dp)
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .shadow(1.dp, backgroundShape)
//            .heightIn(min = 64.dp)
//            .background(Color.White, backgroundShape)
//    ) {
//        NoteColor(
//            color = Color.fromHex(note.color.hex), size = 5.dp, border = 1.dp
//        )
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .align(Alignment.CenterVertically)
//        )
//        {
//            Text(
//                text = note.title,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    letterSpacing = 0.15.sp
//                )
//            )
//            Text(text = note.content)
//        }
//        if (note.isCheckedOff != null) {
//            Checkbox(
//                checked = note.isCheckedOff,
//                onCheckedChange = { isChecked ->
//                    val newNote = note.copy(isCheckedOff = isChecked)
//                    onNoteCheckedChange(newNote)
//                },
//                modifier = Modifier
//                    .padding(start = 8.dp)
//                    .align(Alignment.CenterVertically)
//            )
//        }
//
//    }
}
