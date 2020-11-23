package com.example.notepad.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val desc: String,
    val date: String
)