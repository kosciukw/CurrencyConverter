package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepository

class SaveCurrencyPreferencesUseCase(private val currencyPreferencesRepository: CurrencyPreferencesRepository) :
    UseCase<Unit, Pair<String, Int>>() {

    override suspend fun action(params: Pair<String, Int>) {
        return currencyPreferencesRepository.saveCurrencyPosition(
            params.first,
            params.second
        )
    }
}