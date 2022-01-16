package com.kosciukvictor.currencyconverter.domain.repositories.preferences

import android.content.SharedPreferences
import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO


class CurrencyPreferencesRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    CurrencyPreferencesRepository {

    override suspend fun saveCurrencyPosition(key: String, userChoice: Int) = try {
        val prefEditor = sharedPreferences.edit()
        prefEditor.putInt(key, userChoice)
        prefEditor.apply()
    } catch (e: Exception) {
        throw e
    }

    override suspend fun getCurrencyPreferences(): Map<String, Int> = try {
        val ratesMap = mutableMapOf<String, Int>()
        val valueFrom = sharedPreferences.getInt(KEY_FROM, DEFAULT_VALUE)
        val valueTo = sharedPreferences.getInt(KEY_TO, DEFAULT_VALUE)
        ratesMap[KEY_FROM] = valueFrom
        ratesMap[KEY_TO]= valueTo
        ratesMap
    } catch (e: Exception) {
        throw e
    }

    companion object{
        const val DEFAULT_VALUE = 0
    }
}