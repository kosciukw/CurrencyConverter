package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverter

class ConvertUseCase(private val currencyConverter: CurrencyConverter) :
    UseCase<String, Triple<Map<String, Int>?, Map<String, Double>?, String?>>() {

    override suspend fun action(params: Triple<Map<String, Int>?, Map<String, Double>?, String?>): String
        = currencyConverter.convertCurrencies(ratesIndexMap = params.first,
        ratesValuesMap = params.second,
        currentEquation = params.third)
}
