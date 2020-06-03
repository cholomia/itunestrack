package com.pocholomia.itunestracker.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pocholomia.itunestracker.local.dao.TrackDao
import com.pocholomia.itunestracker.local.model.TrackDto
import com.pocholomia.itunestracker.local.module.AppDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TrackDaoTest {

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
    @Throws(Exception::class)
    fun writeAndReadInList() {
        val track1 = TrackDto(
            1,
            1,
            "wrapperType",
            "kind",
            1,
            "artistName",
            null,
            null,
            null,
            null,
            null,
            "artworkUrl60",
            "artworkUrl100",
            10.0,
            10.0,
            "explicit",
            1,
            null,
            "ph",
            "date",
            "genre",
            "genre",
            "preview",
            null,
            null
        )
        val track2 = TrackDto(
            2,
            1,
            "wrapperType",
            "kind",
            1,
            "artistName",
            null,
            null,
            null,
            null,
            null,
            "artworkUrl60",
            "artworkUrl100",
            10.0,
            10.0,
            "explicit",
            1,
            null,
            "ph",
            "date",
            "genre",
            "genre",
            "preview",
            null,
            null
        )
        val savedTracks = trackDao.saveAll(track1, track2)
            .blockingGet()

        Assert.assertEquals(2, savedTracks.size)

        val trackList = trackDao.getAll()
            .blockingFirst()
        println("trackList size = ${trackList.size}")
        Assert.assertTrue(trackList.size == 2)

        val track1FromDb = trackDao.getById(1)
            .blockingFirst()
        println(track1FromDb)
        Assert.assertEquals(track1, track1FromDb)

    }


}