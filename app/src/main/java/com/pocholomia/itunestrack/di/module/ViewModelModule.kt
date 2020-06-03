package com.pocholomia.itunestrack.di.module

import androidx.lifecycle.ViewModel
import com.pocholomia.itunestrack.di.mapkey.ViewModelKey
import com.pocholomia.itunestrack.features.tracks.detail.TrackDetailViewModel
import com.pocholomia.itunestrack.features.tracks.list.TrackListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrackListViewModel::class)
    abstract fun trackListViewModel(viewModel: TrackListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackDetailViewModel::class)
    abstract fun trackDetailViewModel(viewModel: TrackDetailViewModel): ViewModel

}