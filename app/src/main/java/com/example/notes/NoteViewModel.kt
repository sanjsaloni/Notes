package com.example.notes

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel()  {
    val allWords: LiveData<List<Note>> = repository.allWords.asLiveData()
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

}
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

