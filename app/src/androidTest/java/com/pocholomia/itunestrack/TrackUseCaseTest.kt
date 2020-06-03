package com.pocholomia.itunestrack

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pocholomia.itunestrack.data.usecase.TracksUseCaseImpl
import com.pocholomia.itunestrack.domain.usecase.TracksUseCase
import com.pocholomia.itunestrack.remote.module.RemoteModule
import com.pocholomia.itunestracker.local.dao.TrackDao
import com.pocholomia.itunestracker.local.module.AppDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TrackUseCaseTest {

    private lateinit var trackDao: TrackDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        trackDao = db.trackDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testMergingOfLocalAndRemoteSource() {
        val remoteModule = RemoteModule()
        val okHttpClient = remoteModule.okHttpClient()
        val retrofit = remoteModule.retrofit(okHttpClient)
        val trackService = remoteModule.tracksService(retrofit)

        val useCase: TracksUseCase = TracksUseCaseImpl(trackService, trackDao)

        val tracks = useCase.getTracks()
            .blockingFirst()

        Assert.assertTrue(tracks.isNotEmpty())
    }

}