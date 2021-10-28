package com.example.jetnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.data.repository.Repository
import com.example.jetnote.domain.model.ColorModel
import com.example.jetnote.domain.model.NoteModel
import com.example.jetnote.routing.JetNotesRouter
import com.example.jetnote.routing.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model used for storing the global app state.
 *
 * This view model is used for all screens.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {
    val notesNotInTrash: LiveData<List<NoteModel>> by lazy {
        repository.getAllNotesNotInTrash()
    }
    private var _noteEntry = MutableLiveData(NoteModel())
    val noteEntry: LiveData<NoteModel> = _noteEntry
    val colors: LiveData<List<ColorModel>> by lazy { repository.getAllColors() }

    //Trash 1
    val notesInTrash by lazy { repository.getAllNotesInTrash() }
    private var _selectedNotes = MutableLiveData<List<NoteModel>>(listOf())
    val selectedNotes: LiveData<List<NoteModel>> = _selectedNotes
    fun onCreateNewNoteClick() {
        JetNotesRouter.navigateTo(Screen.SaveNote)
    }

    fun onNoteClick(note: NoteModel) {
        _noteEntry.value = note
        JetNotesRouter.navigateTo(Screen.SaveNote)
    }

    fun onNoteCheckedChange(note: NoteModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertNote(note)
        }
    }

    fun onNoteEntryChange(note: NoteModel) {
        _noteEntry.value = note
    }

    fun saveNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertNote(note)
            withContext(Dispatchers.Main) {
                JetNotesRouter.navigateTo(Screen.Notes)
                _noteEntry.value = NoteModel()
            }
        }
    }
    fun moveNoteToTrash(note: NoteModel){
        viewModelScope.launch(Dispatchers.Main) {
            JetNotesRouter.navigateTo(Screen.Notes)
        }
    }
    fun onNoteSelected(note: NoteModel){
        _selectedNotes.value = _selectedNotes.value!!.toMutableList().apply {
            if (contains(note)){
                remove(note)
            }else{
                add(note)
            }

        }
    }
    fun restoreNotes(notes: List<NoteModel>){
        viewModelScope.launch(Dispatchers.Default){
            repository.restoreNotesFromTrash(notes.map { it.id })
            withContext(Dispatchers.Main){
                _selectedNotes.value = listOf()
            }
        }
    }
}

