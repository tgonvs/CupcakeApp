package com.dmribeiro87.cupcakeapp

import android.app.Application
import com.dmribeiro87.cupcakeapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Myapplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Myapplication)
            modules(appModule)
        }
    }
}