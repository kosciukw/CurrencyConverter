package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.converter.models.ConvertData
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverter

class ConvertUseCase(
    private val currencyConverter: CurrencyConverter
) : UseCase<String, ConvertData>() {

    override suspend fun action(convertData: ConvertData): String =
        currencyConverter.convertCurrencies(
            ratesIndexMap = convertData.ratesIndexMap,
            ratesValuesMap = convertData.ratesValuesMap,
            currentEquation = convertData.currentEquation
        )
}
