package com.pocholomia.itunestrack.domain.usecase

import com.pocholomia.itunestrack.domain.model.Track
import io.reactivex.Flowable
import io.reactivex.Single

interface TracksUseCase {

    fun getTracks(): Flowable<List<Track>>

    fun refreshTracks(): Single<List<Track>>

    fun getTrack(trackId: Long): Flowable<Track>

}