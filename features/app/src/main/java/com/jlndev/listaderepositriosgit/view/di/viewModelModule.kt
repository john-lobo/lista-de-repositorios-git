package com.jlndev.listaderepositriosgit.view.di

import com.jlndev.baseservice.ext.BaseSchedulerProvider
import com.jlndev.baseservice.ext.SchedulerProvider
import com.jlndev.listaderepositriosgit.view.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single <BaseSchedulerProvider>{ SchedulerProvider() }
    viewModel { HomeViewModel(get(),get()) }
}