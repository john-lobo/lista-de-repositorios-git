package com.jlndev.listaderepositriosgit.application

import android.app.Application
import com.jlndev.githubservice.di.dataModuleGithubService
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
                add(dataModuleGithubService)
            }
            modules(modules)
        }
    }
}