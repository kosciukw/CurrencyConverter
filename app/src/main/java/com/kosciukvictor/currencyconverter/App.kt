package com.kosciukvictor.currencyconverter

import android.app.Application
import com.kosciukvictor.currencyconverter.di.apiModule
import com.kosciukvictor.currencyconverter.di.appModule
import com.kosciukvictor.currencyconverter.di.preferences.preferencesModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

@ExperimentalCoroutinesApi

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@App)
            modules(provideModules())
        }
    }

    private fun provideModules(): List<Module> {
        return listOf(
            appModule,
            apiModule,
            preferencesModule
        )
    }
}