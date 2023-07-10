package com.example.simbirsoft.data.remote

import com.example.simbirsoft.data.local.model.NoteLocalModel
import com.example.simbirsoft.data.remote.model.NoteRemoteModel
import com.example.simbirsoft.data.remote.model.NotesRemoteModel
import com.example.simbirsoft.data.remote.model.TimestampDeserializer
import com.example.simbirsoft.data.utils.toNoteLocalModel
import com.google.gson.GsonBuilder
import java.sql.Timestamp

fun initNotes(): List<NoteRemoteModel> {
    val json = """
        {
          "notes": [
            {
              "id": 1,
              "date_start": 147600000,
              "date_finish": 147610000,
              "name": "My task",
              "description": "Описание моего дела"
            },
            {
              "id": 2,
              "date_start": 147700000,
              "date_finish": 147710000,
              "name": "Another task",
              "description": "Описание другого дела"
            },
            {
              "id": 3,
              "date_start": 147800000,
              "date_finish": 147810000,
              "name": "Third task",
              "description": "Описание третьего дела"
            }
          ]
        }
    """

    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(Timestamp::class.java, TimestampDeserializer())
    val gson = gsonBuilder.create()

    val notesRemoteModel = gson.fromJson(json, NotesRemoteModel::class.java)

    return notesRemoteModel.notes
}


fun addNotesFromJson(): List<NoteLocalModel> {
    val remoteNotes = initNotes()
    val notesDataBase = mutableListOf<NoteLocalModel>()
    for (note in remoteNotes) {
        notesDataBase.add(note.toNoteLocalModel())
    }
    return notesDataBase
}
