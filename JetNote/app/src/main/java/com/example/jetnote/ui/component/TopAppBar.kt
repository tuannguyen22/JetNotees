package com.example.jetnote.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopAppBar(
    title: String,
    icon: ImageVector,
    onIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = MaterialTheme.colors.primarySurface)
    ) {
        Image(
            imageVector = icon,
            contentDescription = "Top App Bar Icon",
            colorFilter = ColorFilter.tint(
                MaterialTheme.colors.onPrimary
            ),
            modifier = Modifier
                .clickable(onClick = onIconClick)
                .padding(16.dp)
                .align(Alignment.CenterVertically)
                .size(50.dp)
        )
        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 0.15.sp,
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

