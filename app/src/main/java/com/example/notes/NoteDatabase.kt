package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KParameter


@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
    public abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()
                    noteDao.deleteAll()
                    var note = Note("Hello")
                    noteDao.insert(note)
                    note=Note("World!")
                    noteDao.insert(note)


                }
            }
        }

        suspend fun populateDatabase(NoteDao: NoteDao) {
            // Delete all content here.
            NoteDao.deleteAll()
        }
    }
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

