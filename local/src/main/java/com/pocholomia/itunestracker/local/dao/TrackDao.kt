package com.pocholomia.itunestracker.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pocholomia.itunestracker.local.model.TrackDto
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TrackDao {

    @Query("SELECT * FROM track")
    fun getAll(): Flowable<List<TrackDto>>

    @Query("SELECT * FROM track WHERE id = :trackId")
    fun getById(trackId: Long): Flowable<TrackDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg tracks: TrackDto): Single<List<Long>>

    @Query("DELETE FROM track")
    fun deleteAll()

}