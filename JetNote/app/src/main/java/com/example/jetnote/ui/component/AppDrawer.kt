package com.example.jetnote.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnote.domain.model.ColorModel
import com.example.jetnote.routing.Screen
import com.example.jetnote.theme.JetNotesThemeSettings
import com.example.jetnote.ui.screens.ColorPicker

@Composable
private fun AppDrawerHeader() {
    Row{
        Image(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Drawer header Icon",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
        Text(text = "JetNotes", modifier = Modifier.align(CenterVertically))
    }
}

@Preview
@Composable
fun AppDrawerHeaderPreview() {
    AppDrawerHeader()
}



@Composable
private fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colors

    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }

    val textColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }

    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        colors.surface
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Image(
                imageVector = icon, contentDescription = "Screen Navigation Button",
                colorFilter = ColorFilter.tint(textColor),
                alpha = imageAlpha
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun LightDarkThemeItem() {
    Row {
        Text(
            text = "Turn On",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
        Switch(
            checked = JetNotesThemeSettings.isDarkThemeEnabled,
            onCheckedChange = { JetNotesThemeSettings.isDarkThemeEnabled = it },
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .align(alignment = CenterVertically)
        )
    }
}


@Composable
fun AppDrawer(
    currentScreen: Screen,
    closedDrawerAction: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ScreenNavigationButton(icon = Icons.Filled.Home, label = "Notes", isSelected = true){}
        ScreenNavigationButton(icon = Icons.Filled.Delete, label = "Draft", isSelected = true){}
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        Row {
            Text(text = "Enable Dark Theme")
        }
        Row {
            LightDarkThemeItem()
        }
        ColorPicker(
            colors = listOf(
                ColorModel.DEFAULT,
                ColorModel.DEFAULT,
                ColorModel.DEFAULT
            )
        ){}

    }
}