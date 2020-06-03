package com.pocholomia.itunestrack.data.module

import com.pocholomia.itunestrack.data.usecase.TracksUseCaseImpl
import com.pocholomia.itunestrack.domain.usecase.TracksUseCase
import com.pocholomia.itunestrack.remote.module.RemoteModule
import com.pocholomia.itunestrack.remote.service.TrackService
import com.pocholomia.itunestracker.local.dao.TrackDao
import com.pocholomia.itunestracker.local.module.LocalModule
import dagger.Module
import dagger.Provides

@Module(includes = [LocalModule::class, RemoteModule::class])
class DataModule {

    @Provides
    fun tracksUseCase(
        trackService: TrackService,
        trackDao: TrackDao
    ): TracksUseCase = TracksUseCaseImpl(trackService, trackDao)

}