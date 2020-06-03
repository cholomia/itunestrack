package com.pocholomia.itunestrack.utils.mvi

typealias Reducer<S, C> = (state: S, change: C) -> S