package com.kosciukvictor.currencyconverter.di.preferences
import android.content.Context
import android.content.SharedPreferences
import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepository
import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepositoryImpl
import com.kosciukvictor.currencyconverter.domain.utils.SHARED_PREFS
import org.koin.dsl.module

val preferencesModule = module {

    single<CurrencyPreferencesRepository> { CurrencyPreferencesRepositoryImpl(sharedPreferences = get()) }

    single { provideSharedPreferences(context = get()) }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
}