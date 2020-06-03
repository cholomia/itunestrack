package com.pocholomia.itunestrack.remote

import com.pocholomia.itunestrack.remote.module.RemoteModule
import org.junit.Assert
import org.junit.Test

/**
 * quick Api test and does not use dependency injection for quick use
 */
class TracksServiceTest {

    @Test
    fun testTracksApiService() {
        val remoteModule = RemoteModule()
        val okHttpClient = remoteModule.okHttpClient()
        val retrofit = remoteModule.retrofit(okHttpClient)
        val trackService = remoteModule.tracksService(retrofit)

        val tracksApiResponse = trackService.getTracks()
            .blockingGet()

        Assert.assertTrue(tracksApiResponse.resultCount > 0)
        Assert.assertTrue(tracksApiResponse.tracks.size == tracksApiResponse.resultCount)

        println("KINDS")
        tracksApiResponse.tracks//.distinctBy { "${it.kind} ${it.wrapperType}" }
            .forEach { println("${it.kind} ${it.wrapperType}") }
    }

}