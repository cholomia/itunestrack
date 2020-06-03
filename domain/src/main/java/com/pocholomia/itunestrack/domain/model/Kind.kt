package com.pocholomia.itunestrack.domain.model

enum class Kind {

    SONG {
        override val value: String
            get() = "song"
        override val displayName: String
            get() = "Songs"
    },
    MOVIE {
        override val value: String
            get() = "feature-movie"
        override val displayName: String
            get() = "Movies"
    },
    TV_EPISODE {
        override val value: String
            get() = "tv-episode"
        override val displayName: String
            get() = "TV Episodes"
    },
    AUDIO_BOOK {
        override val value: String
            get() = "audiobook"
        override val displayName: String
            get() = "Audiobooks"
    };

    abstract val value: String

    abstract val displayName: String
}