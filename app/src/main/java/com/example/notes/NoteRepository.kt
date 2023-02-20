package com.example.notes

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val NoteDao: NoteDao) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.
        val allWords: Flow<List<Note>> = NoteDao.getAlphabetizedWords()

        // By default Room runs suspend queries off the main thread, therefore, we don't need to
        // implement anything else to ensure we're not doing long running database work
        // off the main thread.
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(note: Note) {
            NoteDao.insert(note)
        }
    }
