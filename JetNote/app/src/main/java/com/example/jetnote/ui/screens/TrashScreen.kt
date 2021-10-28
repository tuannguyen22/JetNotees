package com.example.jetnote.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import com.example.jetnote.R
import com.example.jetnote.domain.model.NoteModel
import com.example.jetnote.routing.Screen
import com.example.jetnote.ui.component.AppDrawer
import com.example.jetnote.ui.component.Note
import com.example.jetnote.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.lang.RuntimeException

private const val NO_DIALOG = 1
private const val RESTORE_NOTES_DIALOG = 2
private const val PERMANENTLY_DELETE_DIALOG = 3


@Composable
@ExperimentalMaterialApi
fun TrashScreen(viewModel: MainViewModel) {
    val notesInTrash: List<NoteModel> by viewModel.notesNotInTrash.observeAsState(listOf())
    val selectedNotes: List<NoteModel> by viewModel.selectedNotes.observeAsState(listOf())
    val dialogState: MutableState<Int> = rememberSaveable { mutableStateOf(NO_DIALOG) }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            val areActionVisible = selectedNotes.isEmpty()
            TrashTopAppBar(
                onNavigationIconClick = { coroutineScope.launch { scaffoldState.drawerState.open() } },
                onRestoreNotesClick = { dialogState.value = RESTORE_NOTES_DIALOG },
                onDeleteNotesClick = { dialogState.value = PERMANENTLY_DELETE_DIALOG },
                areActionVisible = areActionVisible
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(currentScreen = Screen.Trash,
                closedDrawerAction = {
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                })
        },
        content = {
            Content(
                notes = notesInTrash,
                onNoteClick = { viewModel.onNoteSelected(it) },
                onNoteCheckedChange = {viewModel.onNoteCheckedChange(it)},
                selectedNotes = selectedNotes
            )
        }
    )
    val dialog = dialogState.value
    if (dialog != NO_DIALOG) {
        val conform: () -> Unit = when (dialog) {
            RESTORE_NOTES_DIALOG -> {
                {
                    viewModel.restoreNotes(selectedNotes)
                    dialogState.value = NO_DIALOG
                }
            }
            PERMANENTLY_DELETE_DIALOG -> {
                {
                    viewModel
                }

            }
            else -> {
                {
                    dialogState.value = NO_DIALOG
                }
            }
        }
        AlertDialog(
            onDismissRequest = { dialogState.value = NO_DIALOG },
            title = {
                Text(mapDialogTitle(dialog))
            },
            text = {
                Text(mapDialogText(dialog))
            },
            confirmButton = {
                TextButton(onClick = { conform }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogState.value = NO_DIALOG }) {
                    Text(text = "Dismiss")
                }
            }
        )
    }

}

@Composable
fun TrashTopAppBar(
    onNavigationIconClick: () -> Unit,
    onRestoreNotesClick: () -> Unit,
    onDeleteNotesClick: () -> Unit,
    areActionVisible: Boolean
) {
    TopAppBar(title = { Text(text = "Trash") }, navigationIcon = {
        IconButton(onClick = onNavigationIconClick) {
            Icon(imageVector = Icons.Filled.List, contentDescription = "Drawer")
        }
    },
        actions = {
            if (areActionVisible) {
                IconButton(onClick = onRestoreNotesClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_color_lens_24),
                        contentDescription = "Restore Notes Button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                IconButton(onClick = onDeleteNotesClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_color_lens_24),
                        contentDescription = "Restore Notes Button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
@ExperimentalMaterialApi
fun Content(
    notes: List<NoteModel>,
    onNoteCheckedChange : (NoteModel) -> Unit,
    onNoteClick: (NoteModel) -> Unit,
    selectedNotes: List<NoteModel>
) {
    val tabs = listOf("REGULAR", "CHECKABLE")
    val selectedTab by remember {
        mutableStateOf(0)
    }
    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = (title)) },
                    selected = selectedTab == index,
                    onClick = { selectedTab == index }
                )
            }
        }
        val filterNotes = when (selectedTab) {
            0 -> {
                notes.filter { it.isCheckedOff == null }
            }
            1 -> {
                notes.filter { it.isCheckedOff == null }
            }
            else -> throw IllegalStateException("tab not support - index: $selectedTab")
        }
        LazyColumn {
            items(count = filterNotes.size) { noteIndex ->
                val note = filterNotes[noteIndex]
                val selected = selectedNotes.contains(note)
                Note(
                    note = note,
                    onNoteCheckedChange = onNoteCheckedChange,
                    onNoteClick = onNoteClick,
                    isSelected = selected
                )
            }
        }
    }
}

private fun mapDialogTitle(dialog: Int): String = when (dialog) {
    RESTORE_NOTES_DIALOG -> "Restore note"
    PERMANENTLY_DELETE_DIALOG -> "Delete Notes Forever"
    else -> throw RuntimeException("Dialog not supported")
}

private fun mapDialogText(dialog: Int): String = when (dialog) {
    RESTORE_NOTES_DIALOG -> "Are you sure want to restores?"
    PERMANENTLY_DELETE_DIALOG -> "Are you sure  want to delete?"
    else -> throw RuntimeException("Dialog not supported")
}