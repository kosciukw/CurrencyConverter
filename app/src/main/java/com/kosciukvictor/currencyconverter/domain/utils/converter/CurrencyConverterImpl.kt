package com.kosciukvictor.currencyconverter.domain.utils.converter

import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculator
import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatter
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapper

class CurrencyConverterImpl(
    private val formatter: CurrencyFormatter,
    private val calculator: ConverterCalculator,
    private val keyMapper: KeyMapper
) : CurrencyConverter {

    override fun convertCurrencies(
        ratesIndexMap: Map<String, Int>?,
        ratesValuesMap: Map<String, Double>?,
        currentEquation: String?
    ): String {

        val inputsNotValid =
            currentEquation.isNullOrEmpty() || ratesValuesMap == null || ratesIndexMap == null


        if (inputsNotValid) {
            return ""
        }

        val keyFrom = keyMapper.mapFrom(ratesIndexMap!!, ratesValuesMap!!)
        val keyTo = keyMapper.mapTo(ratesIndexMap, ratesValuesMap)

        if (keyFrom == null || keyTo == null)
            throw IllegalStateException("Keys cannot be null")

        val rateFrom: Double? = ratesValuesMap[keyFrom]
        val rateTo: Double? = ratesValuesMap[keyTo]

        if (rateFrom == null || rateTo == null)
            throw IllegalStateException("Rates cannot be null")

        val input: Double = currentEquation!!.toDouble()
        val displayInput = formatter.priceToString(input)

        val output = calculator.calculate(rateFrom, rateTo, currentEquation.toDouble())
        val displayOutput = formatter.priceToString(output)

        return displayInput
            .plus(" $keyFrom") + " = \n" + displayOutput
            .plus(" $keyTo")


    }
}