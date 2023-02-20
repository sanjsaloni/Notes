package com.example.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(
           @ColumnInfo(name = "note") val word: String){
@PrimaryKey(autoGenerate = true) var id =0
}

