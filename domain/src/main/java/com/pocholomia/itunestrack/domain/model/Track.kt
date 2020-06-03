package com.pocholomia.itunestrack.domain.model

data class Track(
    val trackId: Long,
    val collectionId: Long,
    val wrapperType: String,
    val kind: Kind?,
    val artistId: Long,
    val artistName: String,
    val trackName: String?,
    val collectionName: String?,
    val collectionCensoredName: String?,
    val artistViewUrl: String?,
    val collectionViewUrl: String?,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val collectionPrice: Double,
    val collectionExplicitness: String,
    val trackCount: Int,
    val copyright: String?,
    val country: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val previewUrl: String,
    val description: String?,
    val longDescription: String?
) {

    val title: String?
        get() = when (kind) {
            Kind.SONG -> trackName
            Kind.MOVIE -> trackName
            Kind.TV_EPISODE -> "$collectionName - $trackName"
            Kind.AUDIO_BOOK -> collectionName
            null -> "unknown"
        }

    val priceDisplay: String
        get() = "$currency ${if (trackPrice > 0.0) trackPrice else collectionPrice}"

}