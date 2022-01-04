package com.kosciukvictor.currencyconverter.domain.api

import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates
import retrofit2.http.GET

interface CurrencyExchangeService {
    @GET("/api/latest?access_key=7b249e9a3366a860cf6edebb825608a4")
    suspend fun getLatest(): ApiRates
}
