package com.pocholomia.itunestrack.remote.module

import com.pocholomia.itunestrack.remote.service.TrackService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    /**
     * for actual project, set base url on build config instead based on build type
     */
    private val baseUrl = "https://itunes.apple.com/"

    /**
     * for demo purposes only, logging interceptor is always set to Body level regardless of debug or release
     * for actual project, set as parameter of the method wherein the dependency will be based on build type
     */
    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun tracksService(retrofit: Retrofit): TrackService =
        retrofit.create(TrackService::class.java)

}