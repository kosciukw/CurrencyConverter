package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepository

class GetCurrencyPreferencesUseCase(
    private val currencyPreferencesRepository: CurrencyPreferencesRepository
) :
    UseCase<Map<String, Int>, Unit>() {
    override suspend fun action(params: Unit): Map<String, Int> =
        currencyPreferencesRepository.getCurrencyPreferences()
}