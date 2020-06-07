package com.pocholomia.itunestrack.data.usecase

import com.pocholomia.itunestrack.data.utils.toTrack
import com.pocholomia.itunestrack.data.utils.toTrackDto
import com.pocholomia.itunestrack.domain.model.Track
import com.pocholomia.itunestrack.domain.usecase.TracksUseCase
import com.pocholomia.itunestrack.remote.service.TrackService
import com.pocholomia.itunestracker.local.dao.TrackDao
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * implementation of track usecase
 */
class TracksUseCaseImpl constructor(
    private val trackService: TrackService,
    private val trackDao: TrackDao
) : TracksUseCase {

    /**
     * for demo app, simple merging of local and remote source, get local first then refresh
     * for actual projects, better to use single source of truth with cache status checking similar to https://github.com/dropbox/store
     */
    override fun getTracks(): Flowable<List<Track>> = getTracksLocal()
        .take(1)
        .switchMap { tracksLocal ->
            when {
                tracksLocal.isEmpty() -> refreshTracks()
                    .toFlowable()
                else -> Flowable.just(tracksLocal)
            }
        }
        .switchMap { freshTracks ->
            getTracksLocal()
                .startWith(freshTracks)
        }
        .subscribeOn(Schedulers.io())

    private fun getTracksLocal() = trackDao.getAll()
        .map { it.map { dto -> dto.toTrack } }

    override fun refreshTracks(): Single<List<Track>> = trackService.getTracks()
        .flatMap { response ->
            val list = response.tracks.map { it.toTrackDto }
            trackDao.deleteAll()
            trackDao.saveAll(*list.toTypedArray())
                .map { list.map { it.toTrack } }
        }

    /**
     * for demo app, get only from local source
     * for actual project, use single source of truth with refresh mechanism
     */
    override fun getTrack(trackId: Long): Flowable<Track> = trackDao.getById(trackId)
        .map { it.toTrack }
        .subscribeOn(Schedulers.io())

}