package com.pocholomia.itunestrack.remote.response

import com.pocholomia.itunestrack.remote.model.TrackRepo
import com.squareup.moshi.Json

data class TracksApiResponse(
    @field:Json(name = "resultCount") val resultCount: Int,
    @field:Json(name = "results") val tracks: List<TrackRepo>
)