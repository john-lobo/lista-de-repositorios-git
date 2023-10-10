package com.jlndev.listaderepositriosgit.application

import android.app.Application
import com.jlndev.githubservice.di.githubServiceModule
import com.jlndev.listaderepositriosgit.view.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            val modules = arrayListOf<Module>().apply {
                add(githubServiceModule)
                add(viewModelModule)
            }
            modules(modules)
        }
    }
}