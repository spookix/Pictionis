package com.androidkotlin.pictionis

import android.app.Application
import timber.log.Timber


class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Timber.e("Entrée dans App")
        instance = this

    }
}

