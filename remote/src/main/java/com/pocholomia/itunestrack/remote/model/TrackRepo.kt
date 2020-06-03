package com.pocholomia.itunestrack.remote.model

import com.squareup.moshi.Json

data class TrackRepo(
    @field:Json(name = "trackId") val trackId: Long,
    @field:Json(name = "wrapperType") val wrapperType: String,
    @field:Json(name = "kind") val kind: String? = null,
    @field:Json(name = "artistId") val artistId: Long,
    @field:Json(name = "collectionId") val collectionId: Long,
    @field:Json(name = "artistName") val artistName: String,
    @field:Json(name = "trackName") val trackName: String? = null,
    @field:Json(name = "collectionName") val collectionName: String? = null,
    @field:Json(name = "collectionCensoredName") val collectionCensoredName: String? = null,
    @field:Json(name = "artistViewUrl") val artistViewUrl: String? = null,
    @field:Json(name = "collectionViewUrl") val collectionViewUrl: String? = null,
    @field:Json(name = "artworkUrl60") val artworkUrl60: String,
    @field:Json(name = "artworkUrl100") val artworkUrl100: String,
    @field:Json(name = "trackPrice") val trackPrice: Double,
    @field:Json(name = "collectionPrice") val collectionPrice: Double,
    @field:Json(name = "collectionExplicitness") val collectionExplicitness: String,
    @field:Json(name = "trackCount") val trackCount: Int,
    @field:Json(name = "copyright") val copyright: String? = null,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "currency") val currency: String,
    @field:Json(name = "releaseDate") val releaseDate: String,
    @field:Json(name = "primaryGenreName") val primaryGenreName: String,
    @field:Json(name = "previewUrl") val previewUrl: String,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "longDescription") val longDescription: String? = null
)