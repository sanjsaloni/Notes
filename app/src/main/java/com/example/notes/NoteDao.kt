package com.example.notes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
//    @Insert()

//    @Delete()

    @Query("SELECT * FROM note_table ORDER BY note ASC")
    fun getAlphabetizedWords(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(word: Note)

    @Query("DELETE FROM note_table")
     fun deleteAll()
}
