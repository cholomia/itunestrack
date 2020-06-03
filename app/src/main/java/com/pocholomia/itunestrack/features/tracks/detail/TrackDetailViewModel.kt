package com.pocholomia.itunestrack.features.tracks.detail

import com.pocholomia.itunestrack.domain.model.Track
import com.pocholomia.itunestrack.domain.usecase.TracksUseCase
import com.pocholomia.itunestrack.utils.SingleEvent
import com.pocholomia.itunestrack.utils.mvi.MviViewModel
import com.pocholomia.itunestrack.utils.mvi.Reducer
import io.reactivex.Flowable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrackDetailViewModel @Inject constructor(
    private val tracksUseCase: TracksUseCase
) : MviViewModel<TrackDetailViewModel.Action, TrackDetailViewModel.State>() {

    sealed class Action {

        data class GetTrack(val trackId: Long) : Action()

    }

    sealed class Change {

        data class SetTrack(val track: Track) : Change()

        data class Error(val error: Throwable) : Change()

    }

    data class State(
        val track: Track? = null,
        val error: SingleEvent<Throwable>? = null
    )

    override val initialState: State
        get() = State()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.SetTrack -> state.copy(track = change.track)
            is Change.Error -> state.copy(error = SingleEvent(
                change.error
            )
            )
        }
    }

    init {
        val getTrackAction = actions.ofType<Action.GetTrack>()
            .switchMap { getTrackFlowable(it.trackId) }

        val states = Flowable
            .mergeArray(
                getTrackAction
            )
            .onErrorReturn { Change.Error(it) }
            .scan(initialState, reducer)
            .distinctUntilChanged()

        subscribe(states)
    }

    private fun getTrackFlowable(trackId: Long): Flowable<Change> = tracksUseCase.getTrack(trackId)
        .map<Change> { Change.SetTrack(it) }
        .onErrorReturn { Change.Error(it) }
        .subscribeOn(Schedulers.io())


}