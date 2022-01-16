package com.kosciukvictor.currencyconverter.domain.utils.keymapper

import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO


class KeyMapperImpl : KeyMapper {

    override fun mapFrom(
        ratesIndexMap: Map<String, Int>,
        rates: Map<String, Double>
    ): String? = mapKeys(ratesIndexMap, rates, KEY_FROM)


    override fun mapTo(
        ratesIndexMap: Map<String, Int>,
        rates: Map<String, Double>
    ): String? = mapKeys(ratesIndexMap, rates, KEY_TO)


    private fun mapKeys(
        ratesIndexMap: Map<String, Int>,
        rates: Map<String, Double>,
        key: String
    ): String? {
        val positionFrom = ratesIndexMap[KEY_FROM]
        val positionTo = ratesIndexMap[KEY_TO]

        val keyList = ArrayList<String>(rates.keys)

        val keyFrom: String = keyList[positionFrom!!]
        val keyTo: String = keyList[positionTo!!]

        return when (key) {
            KEY_TO -> keyTo
            KEY_FROM -> keyFrom
            else -> null
        }
    }
}