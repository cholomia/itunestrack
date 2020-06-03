package com.pocholomia.itunestracker.local.module

import android.content.Context
import androidx.room.Room
import com.pocholomia.itunestracker.local.dao.TrackDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun database(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "db-itunestrack")
            .build()

    @Singleton
    @Provides
    fun trackDao(database: AppDatabase): TrackDao = database.trackDao()

}