package com.example.jetnote

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.example.jetnote.theme.JetNotesTheme
import com.example.jetnote.ui.screens.NoteScreen
import com.example.jetnote.viewmodel.MainViewModel
import com.example.jetnote.viewmodel.MainViewModelFactory

/**
 * Main activity for the app.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            this,
            (application as JetNotesApplication).dependencyInjector.repository
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetNotesTheme {
                NoteScreen(viewModel = viewModel)
            }
        }
    }
}

