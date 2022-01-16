package com.kosciukvictor.currencyconverter.domain.utils.keymapper

interface KeyMapper {

    fun mapFrom(
        ratesIndexMap: Map<String, Int>,
        rates: Map<String, Double>
    ) : String?

    fun mapTo(
        ratesIndexMap: Map<String, Int>,
        rates: Map<String, Double>
    ) : String?
}