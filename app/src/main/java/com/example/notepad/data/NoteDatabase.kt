package com.example.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepad.model.Note

@Database(entities = [Note ::class],version = 1,exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object{

        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context : Context) : NoteDatabase{
            val temIns = INSTANCE
            if(temIns != null){
                return temIns
            }
            synchronized(this){
               val instance = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java,
                   "note_database"
                   ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}