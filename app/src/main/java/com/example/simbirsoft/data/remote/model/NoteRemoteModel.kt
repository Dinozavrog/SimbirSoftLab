package com.example.simbirsoft.data.remote.model


import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.sql.Timestamp

data class NotesRemoteModel(
    @SerializedName("notes")
    val notes: List<NoteRemoteModel>
)

data class NoteRemoteModel(
    @SerializedName("date_finish")
    val dateFinish: Timestamp,
    @SerializedName("date_start")
    val dateStart: Timestamp,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

class TimestampDeserializer : JsonDeserializer<Timestamp> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Timestamp {
        val timestampValue = json.asJsonPrimitive.asLong
        return Timestamp(timestampValue)
    }
}