package com.pocholomia.itunestrack.di.module

import android.content.Context
import android.content.SharedPreferences
import com.pocholomia.itunestrack.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun appContext(myApp: MyApp): Context = myApp.applicationContext

    @Singleton
    @Provides
    fun sharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("itunestrack", Context.MODE_PRIVATE)

}