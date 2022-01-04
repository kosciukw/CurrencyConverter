package com.kosciukvictor.currencyconverter.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kosciukvictor.currencyconverter.domain.api.CurrencyExchangeService
import com.kosciukvictor.currencyconverter.domain.utils.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    factory<String> { provideApiURL() }
    factory<Cache> { provideHttpCache(application = get()) }
    factory<Gson> { provideGson() }
    factory<OkHttpClient> { provideOkhttpClient(cache = get()) }
    single<Retrofit> { provideRetrofit(baseUrl = get(), gson = get(), okHttpClient = get()) }

    factory<CurrencyExchangeService> { provideApiClient(retrofit = get()) }
}

fun provideRetrofit(
    baseUrl: String,
    gson: Gson,
    okHttpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

fun provideApiClient(retrofit: Retrofit): CurrencyExchangeService =
    retrofit.create(CurrencyExchangeService::class.java)

fun provideHttpCache(application: Application): Cache = Cache(
    application.cacheDir, (10 * 1024 * 1024)
        .toLong()
)

fun provideGson(): Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()


fun provideOkhttpClient(cache: Cache): OkHttpClient = OkHttpClient
    .Builder().cache(cache)
    .build()

fun provideApiURL() = BASE_URL