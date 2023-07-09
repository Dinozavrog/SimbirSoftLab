package com.example.simbirsoft.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simbirsoft.data.local.dao.NoteDao
import com.example.simbirsoft.data.local.model.NoteLocalModel
import com.example.simbirsoft.data.local.typeConverters.TimestampConverter

@Database(entities = [NoteLocalModel:: class], version = 2)
@TypeConverters(TimestampConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

}