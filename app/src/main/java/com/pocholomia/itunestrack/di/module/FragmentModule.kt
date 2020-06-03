package com.pocholomia.itunestrack.di.module

import com.pocholomia.itunestrack.features.tracks.detail.TrackDetailFragment
import com.pocholomia.itunestrack.features.tracks.list.TrackListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun trackListFragment(): TrackListFragment

    @ContributesAndroidInjector
    abstract fun trackDetailFragment(): TrackDetailFragment

}