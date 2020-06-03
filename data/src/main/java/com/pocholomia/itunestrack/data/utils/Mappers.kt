package com.pocholomia.itunestrack.data.utils

import com.pocholomia.itunestrack.domain.model.Kind
import com.pocholomia.itunestrack.domain.model.Track
import com.pocholomia.itunestrack.remote.model.TrackRepo
import com.pocholomia.itunestracker.local.model.TrackDto

val TrackRepo.toTrackDto
    get() = TrackDto(
        id = this.trackId,
        collectionId = this.collectionId,
        wrapperType = this.wrapperType,
        kind = this.kind,
        artistId = this.artistId,
        artistName = this.artistName,
        trackName = this.trackName,
        collectionName = this.collectionName,
        collectionCensoredName = this.collectionCensoredName,
        artistViewUrl = this.artistViewUrl,
        collectionViewUrl = this.collectionViewUrl,
        artworkUrl60 = this.artworkUrl60,
        artworkUrl100 = this.artworkUrl100,
        trackPrice = this.trackPrice,
        collectionPrice = this.collectionPrice,
        collectionExplicitness = this.collectionExplicitness,
        trackCount = this.trackCount,
        copyright = this.copyright,
        country = this.country,
        currency = this.currency,
        releaseDate = this.releaseDate,
        primaryGenreName = this.primaryGenreName,
        previewUrl = this.previewUrl,
        description = this.description,
        longDescription = this.longDescription
    )

val TrackDto.toTrack
    get() = Track(
        trackId = this.id,
        collectionId = this.collectionId,
        wrapperType = this.wrapperType,
        kind = this.getKind(),
        artistId = this.artistId,
        artistName = this.artistName,
        trackName = this.trackName,
        collectionName = this.collectionName,
        collectionCensoredName = this.collectionCensoredName,
        artistViewUrl = this.artistViewUrl,
        collectionViewUrl = this.collectionViewUrl,
        artworkUrl60 = this.artworkUrl60,
        artworkUrl100 = this.artworkUrl100,
        trackPrice = this.trackPrice,
        collectionPrice = this.collectionPrice,
        collectionExplicitness = this.collectionExplicitness,
        trackCount = this.trackCount,
        copyright = this.copyright,
        country = this.country,
        currency = this.currency,
        releaseDate = this.releaseDate,
        primaryGenreName = this.primaryGenreName,
        previewUrl = this.previewUrl,
        description = this.description,
        longDescription = this.longDescription
    )

private fun TrackDto.getKind(): Kind? = when (kind) {
    Kind.SONG.value -> Kind.SONG
    Kind.MOVIE.value -> Kind.MOVIE
    Kind.TV_EPISODE.value -> Kind.TV_EPISODE
    else -> if (wrapperType == Kind.AUDIO_BOOK.value) Kind.AUDIO_BOOK else null
}