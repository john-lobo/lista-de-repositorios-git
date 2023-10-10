package com.jlndev.githubservice.di

import com.jlndev.baseservice.network.NetworkProviders
import com.jlndev.githubservice.BuildConfig
import com.jlndev.githubservice.data.GithubRepository
import com.jlndev.githubservice.data.GithubRepositoryImpl
import com.jlndev.githubservice.data.api.GithubService
import org.koin.dsl.module

val dataModuleGithubService = module {
    single { NetworkProviders.providerRetrofit(GithubService::class.java, BuildConfig.BASE_URL) }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
}