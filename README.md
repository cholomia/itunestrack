# itunestrack

A demo application using: 
* Clean Architecture (multi-module setup)
* MVI for presentation layer
* Android Jetpack: LiveData, Room, ViewModel, Navigation,etc.
* Dagger2
* RxJava2
* Retrofit with Moshi

Uses MVI (Model-View-Intent) for a unidirectional and cyclical data flow does a consistent state during the lifecycle of Views.

For persistence library, uses Android Room for simplicity as this demo does not contain deep hierarchy of relationships.

Also added some simple unit test:
* [for Local Module](https://github.com/cholomia/itunestrack/blob/master/local/src/androidTest/java/com/pocholomia/itunestracker/local/TrackDaoTest.kt "TrackDaoTest.kt")
* [for Remote Module](https://github.com/cholomia/itunestrack/blob/master/remote/src/test/java/com/pocholomia/itunestrack/remote/TracksServiceTest.kt "TracksServiceTest.kt")
* [and for integratio of both source](https://github.com/cholomia/itunestrack/blob/master/app/src/androidTest/java/com/pocholomia/itunestrack/TrackUseCaseTest.kt "TrackUseCaseTest.kt")
