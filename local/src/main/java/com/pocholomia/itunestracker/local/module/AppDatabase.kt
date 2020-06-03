package com.pocholomia.itunestracker.local.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pocholomia.itunestracker.local.dao.TrackDao
import com.pocholomia.itunestracker.local.model.TrackDto

@Database(entities = [TrackDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

}