package com.jlndev.githubservice.di

import com.jlndev.baseservice.network.NetworkProviders
import com.jlndev.githubservice.BuildConfig
import com.jlndev.githubservice.data.api.GithubService
import com.jlndev.githubservice.data.db.database.GithubDataBase
import com.jlndev.githubservice.data.service.GithubRepository
import com.jlndev.githubservice.data.service.GithubRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val githubServiceModule = module {
    single { NetworkProviders.providerRetrofit(GithubService::class.java, BuildConfig.BASE_URL) }
    single { GithubDataBase.getInstance(androidContext()).githubPageDao }
    single { GithubDataBase.getInstance(androidContext()).githubRepositoryDao }
    single<GithubRepository> { GithubRepositoryImpl(get(),get(), get()) }
}