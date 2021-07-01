package com.svdroid.pokemonviewer

import android.app.Application
import com.svdroid.pokemonviewer.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by SVDroid on 6/30/21.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(allModules)
        }
    }
}