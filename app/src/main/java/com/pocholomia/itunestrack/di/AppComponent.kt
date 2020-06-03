package com.pocholomia.itunestrack.di

import com.pocholomia.itunestrack.MyApp
import com.pocholomia.itunestrack.data.module.DataModule
import com.pocholomia.itunestrack.di.module.AppModule
import com.pocholomia.itunestrack.di.module.FragmentModule
import com.pocholomia.itunestrack.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        ViewModelModule::class,
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<MyApp>

}