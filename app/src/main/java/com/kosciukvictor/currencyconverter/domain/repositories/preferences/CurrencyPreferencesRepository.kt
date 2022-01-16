package com.kosciukvictor.currencyconverter.domain.repositories.preferences

interface CurrencyPreferencesRepository {

     suspend fun saveCurrencyPosition(key: String, userChoice: Int)

     suspend fun getCurrencyPreferences(): Map<String, Int>
}