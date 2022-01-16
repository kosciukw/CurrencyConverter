package com.kosciukvictor.currencyconverter.domain.utils.converter

interface CurrencyConverter {
    fun convertCurrencies(
        ratesIndexMap : Map<String, Int>?,
        ratesValuesMap: Map<String, Double>?,
        currentEquation: String?
        ): String
}