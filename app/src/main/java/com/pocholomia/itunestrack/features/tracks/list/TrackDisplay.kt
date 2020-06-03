package com.pocholomia.itunestrack.features.tracks.list

import com.pocholomia.itunestrack.domain.model.Track

sealed class TrackDisplay {

    data class Header(val title: String) : TrackDisplay()

    data class Items(val tracks: List<Track>) : TrackDisplay()

}