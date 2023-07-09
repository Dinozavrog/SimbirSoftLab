package com.example.simbirsoft.data.local.typeConverters

import androidx.room.TypeConverter
import java.sql.Timestamp

class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Timestamp? {
        return if (value == null) null else Timestamp(value)
    }

    @TypeConverter
    fun toTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time
    }
}