package com.pocholomia.itunestracker.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class TrackDto(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "collectionId") val collectionId: Long,
    @ColumnInfo(name = "wrapperType") val wrapperType: String,
    @ColumnInfo(name = "kind") val kind: String?,
    @ColumnInfo(name = "artistId") val artistId: Long,
    @ColumnInfo(name = "artistName") val artistName: String,
    @ColumnInfo(name = "trackName") val trackName: String?,
    @ColumnInfo(name = "collectionName") val collectionName: String?,
    @ColumnInfo(name = "collectionCensoredName") val collectionCensoredName: String?,
    @ColumnInfo(name = "artistViewUrl") val artistViewUrl: String?,
    @ColumnInfo(name = "collectionViewUrl") val collectionViewUrl: String?,
    @ColumnInfo(name = "artworkUrl60") val artworkUrl60: String,
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String,
    @ColumnInfo(name = "trackPrice") val trackPrice: Double,
    @ColumnInfo(name = "collectionPrice") val collectionPrice: Double,
    @ColumnInfo(name = "collectionExplicitness") val collectionExplicitness: String,
    @ColumnInfo(name = "trackCount") val trackCount: Int,
    @ColumnInfo(name = "copyright") val copyright: String?,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "primaryGenreName") val primaryGenreName: String,
    @ColumnInfo(name = "previewUrl") val previewUrl: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "longDescription") val longDescription: String?
)