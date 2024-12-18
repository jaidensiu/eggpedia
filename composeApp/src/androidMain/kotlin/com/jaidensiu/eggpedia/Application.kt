package com.jaidensiu.eggpedia

import android.app.Application
import com.jaidensiu.eggpedia.di.KoinInitializer.initKoin
import org.koin.android.ext.koin.androidContext

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@Application)
        }
    }
}
