package com.pocholomia.itunestrack.features.tracks.list

import com.pocholomia.itunestrack.domain.usecase.TracksUseCase
import com.pocholomia.itunestrack.utils.SingleEvent
import com.pocholomia.itunestrack.utils.mvi.MviViewModel
import com.pocholomia.itunestrack.utils.mvi.Reducer
import io.reactivex.Flowable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrackListViewModel @Inject constructor(
    private val tracksUseCase: TracksUseCase
) : MviViewModel<TrackListViewModel.Action, TrackListViewModel.State>() {

    sealed class Action {

        object Refresh : Action()

    }

    sealed class Change {

        data class SetTracks(val tracks: List<TrackDisplay>) : Change()

        data class SetLoading(val isLoading: Boolean) : Change()

        data class Error(val error: Throwable) : Change()

    }

    data class State(
        val tracks: List<TrackDisplay> = emptyList(),
        val isLoading: Boolean = false,
        val error: SingleEvent<Throwable>? = null
    )

    override val initialState: State
        get() = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.SetTracks -> state.copy(tracks = change.tracks, isLoading = false)
            is Change.SetLoading -> state.copy(isLoading = change.isLoading)
            is Change.Error -> state.copy(error = SingleEvent(
                change.error
            )
            )
        }
    }

    init {
        val refreshAction = actions.ofType<Action.Refresh>()
            .switchMap { refreshTracksFlowable() }

        val states = Flowable
            .mergeArray(
                getTracksObservable(),
                refreshAction
            )
            .onErrorReturn { Change.Error(it) }
            .scan(initialState, reducer)
            .distinctUntilChanged()

        subscribe(states)
    }

    private fun getTracksObservable(): Flowable<Change> = tracksUseCase.getTracks()
        .map<Change> { tracks ->
            val trackByKind = tracks
                .filter { it.kind != null }
                .groupBy { it.kind }
            val trackDisplayList = mutableListOf<TrackDisplay>()
            trackByKind.keys.filterNotNull()
                .sortedBy { it.ordinal }
                .forEach {
                    trackDisplayList.add(TrackDisplay.Header(it.displayName))
                    trackDisplayList.add(TrackDisplay.Items(trackByKind[it] ?: emptyList()))
                }
            Change.SetTracks(trackDisplayList)
        }
        .startWith(Change.SetLoading(true))
        .onErrorReturn { Change.Error(it) }
        .subscribeOn(Schedulers.io())

    private fun refreshTracksFlowable(): Flowable<Change> = tracksUseCase.refreshTracks()
        .toFlowable()
        .map<Change> { Change.SetLoading(false) } // no need to repopulate as getTracks() will automatically trigger new items
        .startWith(Change.SetLoading(true))
        .onErrorReturn { Change.Error(it) }
        .subscribeOn(Schedulers.io())


}