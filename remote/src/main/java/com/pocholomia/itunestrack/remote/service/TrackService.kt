package com.pocholomia.itunestrack.remote.service

import com.pocholomia.itunestrack.remote.response.TracksApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TrackService {

    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all")
    fun getTracks(): Single<TracksApiResponse>

}