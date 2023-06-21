package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.api.CurrencyExchangeService
import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates

class GetRatesUseCase(
    private val currencyExchangeService: CurrencyExchangeService
) : UseCase<ApiRates, Unit>() {

    override suspend fun action(params: Unit): ApiRates {
        return currencyExchangeService.getLatest()
    }
}